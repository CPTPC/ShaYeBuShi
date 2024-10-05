package shayebushi.ui.dialogs;

import arc.Core;
import arc.Events;
import arc.files.Fi;
import arc.files.ZipFi;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.Gl;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.scene.Element;
import arc.scene.event.ElementGestureListener;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.scene.event.Touchable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.*;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.content.TechTree;
import mindustry.core.UI;
import mindustry.core.Version;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.*;
import mindustry.gen.Icon;
import mindustry.gen.Iconc;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.PlanetGrid;
import mindustry.graphics.g3d.PlanetRenderer;
import mindustry.input.Binding;
import mindustry.maps.SectorDamage;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.*;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.PlanetDialog;
import mindustry.world.blocks.storage.CoreBlock;
import shayebushi.SYBSUnitTypes;
import shayebushi.ShaYeBuShi;
import shayebushi.maps.planet.BuLaoEnPlanetGenerator;
import shayebushi.maps.planet.DeLeiKePlanetGenerator;

import static arc.Core.*;
import static mindustry.Vars.*;
import static mindustry.graphics.g3d.PlanetRenderer.*;
import static mindustry.graphics.g3d.PlanetRenderer.borderColor;
import static mindustry.ui.dialogs.PlanetDialog.Mode.*;

public class SYBSPlanetDialog extends PlanetDialog {
    public static final String[] defaultIcons = {
            "effect", "power", "logic", "units", "liquid", "production", "defense", "turret", "distribution", "crafting",
            "settings", "cancel", "zoom", "ok", "star", "home", "pencil", "up", "down", "left", "right",
            "hammer", "warning", "tree", "admin", "map", "modePvp", "terrain",
            "modeSurvival", "commandRally", "commandAttack",
    };
    public Texture[] planetTextures;
    public SYBSPlanetDialog() {
        super() ;
        clearListeners();
        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, KeyCode key){
                if(event.targetActor == SYBSPlanetDialog.this && (key == KeyCode.escape || key == KeyCode.back || key == Core.keybinds.get(Binding.planet_map).key)){
                    if(showing() && newPresets.size > 1){
                        //clear all except first, which is the last sector.
                        newPresets.truncate(1);
                    }else if(selected != null){
                        selectSector(null);
                    }else{
                        Core.app.post(() -> hide());
                    }
                    return true;
                }
                return false;
            }
        });
        dragged((cx, cy) -> {
            //no multitouch drag
            if(Core.input.getTouches() > 1) return;

            if(showing()){
                newPresets.clear();
            }

            Vec3 pos = state.camPos;

            float upV = pos.angle(Vec3.Y);
            float xscale = 9f, yscale = 10f;
            float margin = 1;

            //scale X speed depending on polar coordinate
            float speed = 1f - Math.abs(upV - 90) / 90f;

            pos.rotate(state.camUp, cx / xscale * speed);

            //prevent user from scrolling all the way up and glitching it out
            float amount = cy / yscale;
            amount = Mathf.clamp(upV + amount, margin, 180f - margin) - upV;

            pos.rotate(Tmp.v31.set(state.camUp).rotate(state.camDir, 90), amount);
        });

        addListener(new InputListener(){
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY){
                if(event.targetActor == SYBSPlanetDialog.this){
                    zoom = Mathf.clamp(zoom + amountY / 10f, state.planet.minZoom, 2f);
                }
                return true;
            }
        });

        addCaptureListener(new ElementGestureListener(){
            float lastZoom = -1f;

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance){
                if(lastZoom < 0){
                    lastZoom = zoom;
                }

                zoom = (Mathf.clamp(initialDistance / distance * lastZoom, state.planet.minZoom, 2f));
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, KeyCode button){
                lastZoom = zoom;
            }
        });

        shown(this::setup);

        //show selection of Erekir/Serpulo campaign if the user has no bases, and hasn't selected yet (essentially a "have they played campaign before" check)
        shown(() -> {
            if(!settings.getBool("campaignselect") && !content.planets().contains(p -> p.sectors.contains(s -> s.hasBase()))){
                var diag = new BaseDialog("@campaign.select");

                Planet[] selected = {null};
                var group = new ButtonGroup<>();
                group.setMinCheckCount(0);
                state.planet = Planets.sun;
                Planet[] choices = {Planets.serpulo, Planets.erekir};
                int i = 0;
                for(var planet : choices){
                    TextureRegion tex = new TextureRegion(planetTextures[i]);

                    diag.cont.button(b -> {
                        b.top();
                        b.add(planet.localizedName).color(Pal.accent).style(Styles.outlineLabel);
                        b.row();
                        b.image(new TextureRegionDrawable(tex)).grow().scaling(Scaling.fit);
                    }, Styles.togglet, () -> selected[0] = planet).size(mobile ? 220f : 320f).group(group);
                    i ++;
                }

                diag.cont.row();
                diag.cont.label(() -> selected[0] == null ? "@campaign.none" : "@campaign." + selected[0].name).labelAlign(Align.center).style(Styles.outlineLabel).width(440f).wrap().colspan(2);

                diag.buttons.button("@ok", Icon.ok, () -> {
                    state.planet = selected[0];
                    lookAt(state.planet.getStartSector());
                    selectSector(state.planet.getStartSector());
                    settings.put("campaignselect", true);
                    diag.hide();
                }).size(300f, 64f).disabled(b -> selected[0] == null);

                app.post(diag::show);
            }
        });
        Fi target = Version.build == -1 ? Core.files.absolute("D:\\Mindustry-master\\core\\build\\libs\\A-files\\sprites\\planets") : mods.getMod("shayebushi").file != null ? new Seq<Fi>(new ZipFi(mods.getMod("shayebushi").file).list()).find(f -> f.name().equals("sprites")).child("planets") : null ;
        planetTextures = new Texture[]{SYBSUnitTypes.loadPng(target, "serpulo-copy").texture, SYBSUnitTypes.loadPng(target, "erekir-copy").texture} ;
    }

    @Override
    public void renderSectors(Planet planet){

        //draw all sector stuff
        if(state.uiAlpha > 0.01f){
            for(Sector sec : planet.sectors){
                if(canSelect(sec) || sec.unlocked() || debugSelect){
                    Rules r = new Rules() ;
                    sec.planet.ruleSetter.get(r) ;
                    Color enemyColor = sec.save != null ? sec.save.meta.rules.waveTeam.color :
                            sec.planet.generator instanceof SerpuloPlanetGenerator ? Team.crux.color : sec.planet.generator instanceof ErekirPlanetGenerator ? Team.malis.color : sec.planet.generator instanceof BuLaoEnPlanetGenerator ? Team.blue.color : sec.planet.generator instanceof DeLeiKePlanetGenerator ? Team.green.color :
                                    r.waveTeam.color;
                    Color color =
                            sec.hasBase() ? Tmp.c2.set(r.defaultTeam.color).lerp(enemyColor, sec.hasEnemyBase() ? 0.5f : 0f) :
                                    sec.preset != null ?
                                            sec.preset.unlocked() ? Tmp.c2.set(Team.derelict.color).lerp(Color.white, Mathf.absin(Time.time, 10f, 1f)) :
                                                    Color.gray :
                                            sec.hasEnemyBase() ? enemyColor :
                                                    new Color() ;
                    /*
                    color =
                            sec.hasBase() ? Tmp.c2.set(Team.sharded.color).lerp(Team.crux.color, sec.hasEnemyBase() ? 0.5f : 0f) :
                                    sec.preset != null ?
                                            sec.preset.unlocked() ? Tmp.c2.set(Team.derelict.color).lerp(Color.white, Mathf.absin(Time.time, 10f, 1f)) :
                                                    Color.gray :
                                            sec.hasEnemyBase() ? Team.crux.color :
                                                    null;
                    */

                    if(color != null){
                        planets.drawSelection(sec, Tmp.c1.set(color).mul(0.8f).a(state.uiAlpha), 0.026f, -0.001f);
                    }
                }else{
                    planets.fill(sec, Tmp.c1.set(shadowColor).mul(1, 1, 1, state.uiAlpha), -0.001f);
                }
            }
        }

        Sector current = Vars.state.getSector() != null && Vars.state.getSector().isBeingPlayed() && Vars.state.getSector().planet == state.planet ? Vars.state.getSector() : null;

        if(current != null){
            planets.fill(current, hoverColor.write(Tmp.c1).mulA(state.uiAlpha), -0.001f);
        }

        //draw hover border
        if(hovered != null){
            planets.fill(hovered, hoverColor.write(Tmp.c1).mulA(state.uiAlpha), -0.001f);
            planets.drawBorders(hovered, borderColor, state.uiAlpha);
        }

        //draw selected borders
        if(selected != null){
            planets.drawSelection(selected, state.uiAlpha);
            planets.drawBorders(selected, borderColor, state.uiAlpha);
        }

        planets.batch.flush(Gl.triangles);

        if(hovered != null && !hovered.hasBase()){
            Sector launchFrom = findLauncher(hovered);
            if(launchFrom != null && hovered != launchFrom && canSelect(hovered)){
                planets.drawArc(planet, launchFrom.tile.v, hovered.tile.v);
            }
        }

        if(state.uiAlpha > 0.001f){
            for(Sector sec : planet.sectors){
                if(sec.hasBase()){
                    if(planet.allowSectorInvasion){
                        for(Sector enemy : sec.near()){
                            if(enemy.hasEnemyBase()){
                                planets.drawArc(planet, enemy.tile.v, sec.tile.v, Team.crux.color.write(Tmp.c2).a(state.uiAlpha), Color.clear, 0.24f, 110f, 25);
                            }
                        }
                    }

                    if(selected != null && selected != sec && selected.hasBase()){
                        //imports
                        if(sec.info.getRealDestination() == selected && sec.info.anyExports()){
                            planets.drawArc(planet, sec.tile.v, selected.tile.v, Color.gray.write(Tmp.c2).a(state.uiAlpha), Pal.accent.write(Tmp.c3).a(state.uiAlpha), 0.4f, 90f, 25);
                        }
                        //exports
                        if(selected.info.getRealDestination() == sec && selected.info.anyExports()){
                            planets.drawArc(planet, selected.tile.v, sec.tile.v, Pal.place.write(Tmp.c2).a(state.uiAlpha), Pal.accent.write(Tmp.c3).a(state.uiAlpha), 0.4f, 90f, 25);
                        }
                    }
                }
            }
        }
    }
    void setup(){
        searchText = "";
        zoom = state.zoom = 1f;
        state.uiAlpha = 1f;
        ui.minimapfrag.hide();

        clearChildren();

        margin(0f);

        stack(
                new Element(){
                    {
                        //add listener to the background rect, so it doesn't get unnecessary touch input
                        addListener(new ElementGestureListener(){
                            @Override
                            public void tap(InputEvent event, float x, float y, int count, KeyCode button){
                                if(showing()) return;

                                if(hovered != null && selected == hovered && count == 2){
                                    playSelected();
                                }

                                if(hovered != null && (canSelect(hovered) || debugSelect)){
                                    selected = hovered;
                                }

                                if(selected != null){
                                    updateSelected();
                                }
                            }
                        });
                    }

                    @Override
                    public void act(float delta){
                        if(scene.getDialog() == SYBSPlanetDialog.this && !scene.hit(input.mouseX(), input.mouseY(), true).isDescendantOf(e -> e instanceof ScrollPane)){
                            scene.setScrollFocus(SYBSPlanetDialog.this);
                        }

                        super.act(delta);
                    }

                    @Override
                    public void draw(){
                        planets.render(state);
                    }
                },
                //info text
                new Table(t -> {
                    t.touchable = Touchable.disabled;
                    t.top();
                    t.label(() -> mode == select ? "@sectors.select" : "").style(Styles.outlineLabel).color(Pal.accent);
                }),
                buttons,

                // planet selection
                new Table(t -> {
                    t.top().left();
                    ScrollPane pane = new ScrollPane(null, Styles.smallPane);
                    t.add(pane).colspan(2).row();
                    Table starsTable = new Table(Styles.black);
                    pane.setWidget(starsTable);
                    pane.setScrollingDisabled(true, false);

                    int starCount = 0;
                    for(Planet star : content.planets()){
                        if(star.solarSystem != star || !content.planets().contains(p -> p.solarSystem == star && selectable(p))) continue;

                        starCount++;
                        if(starCount > 1) starsTable.add(star.localizedName).padLeft(10f).padBottom(10f).padTop(10f).left().width(190f).row();
                        Table planetTable = new Table();
                        planetTable.margin(4f); //less padding
                        starsTable.add(planetTable).left().row();
                        for(Planet planet : content.planets()){
                            if(planet.solarSystem == star && selectable(planet)){
                                Button planetButton = planetTable.button(planet.localizedName, Icon.icons.get(planet.icon + "Small", Icon.icons.get(planet.icon, Icon.commandRallySmall)), Styles.flatTogglet, () -> {
                                    selected = null;
                                    launchSector = null;
                                    if(state.planet != planet){
                                        newPresets.clear();
                                        state.planet = planet;

                                        rebuildExpand();
                                    }
                                    settings.put("lastplanet", planet.name);
                                }).width(200).height(40).update(bb -> bb.setChecked(state.planet == planet)).with(w -> w.marginLeft(10f)).get();
                                planetButton.getChildren().get(1).setColor(planet.iconColor);
                                planetButton.setColor(planet.iconColor);
                                planetTable.background(Tex.pane).row();
                            }
                        }
                    }
                }).visible(() -> mode != select),

                new Table(c -> {
                    expandTable = c;
                })).grow();

        rebuildExpand();
    }
    @Override
    public void renderProjections(Planet planet){
        float iw = 48f/4f;

        for(Sector sec : planet.sectors){
            if(sec != hovered){
                var preficon = sec.icon();
                var icon =
                        sec.isAttacked() ? Fonts.getLargeIcon("warning") :
                                !sec.hasBase() && sec.preset != null && sec.preset.unlocked() && preficon == null ?
                                        Fonts.getLargeIcon("terrain") :
                                        sec.preset != null && sec.preset.locked() && sec.preset.techNode != null && !sec.preset.techNode.parent.content.locked() ? Fonts.getLargeIcon("lock") :
                                                preficon;
                var color = sec.preset != null && !sec.hasBase() ? Team.derelict.color : Team.sharded.color;

                if(icon != null){
                    planets.drawPlane(sec, () -> {
                        //use white for content icons
                        Draw.color(preficon == icon && sec.info.contentIcon != null ? Color.white : color, state.uiAlpha);
                        Draw.rect(icon, 0, 0, iw, iw * icon.height / icon.width);
                    });
                }
            }
        }

        Draw.reset();

        if(hovered != null && state.uiAlpha > 0.01f){
            planets.drawPlane(hovered, () -> {
                Draw.color(hovered.isAttacked() ? Pal.remove : Color.white, Pal.accent, Mathf.absin(5f, 1f));
                Draw.alpha(state.uiAlpha);

                var icon = hovered.locked() && !canSelect(hovered) ? Fonts.getLargeIcon("lock") : hovered.isAttacked() ? Fonts.getLargeIcon("warning") : hovered.icon();

                if(icon != null){
                    Draw.rect(icon, 0, 0, iw, iw * icon.height / icon.width);
                }

                Draw.reset();
            });
        }

        Draw.reset();
    }
    @Override
    public void act(float delta){
        super.act(delta);

        //update lerp
        if(state.otherCamPos != null){
            state.otherCamAlpha = Mathf.lerpDelta(state.otherCamAlpha, 1f, 0.05f);
            state.camPos.set(0f, camLength, 0.1f);

            if(Mathf.equal(state.otherCamAlpha, 1f, 0.01f)){
                //TODO change zoom too
                state.camPos.set(Tmp.v31.set(state.otherCamPos).lerp(state.planet.position, state.otherCamAlpha).add(state.camPos).sub(state.planet.position));

                state.otherCamPos = null;
                //announce new sector
                newPresets.add(state.planet.sectors.get(state.planet.startSector));

            }
        }

        if(hovered != null && !mobile && state.planet.hasGrid()){
            addChild(hoverLabel);
            hoverLabel.toFront();
            hoverLabel.touchable = Touchable.disabled;
            hoverLabel.color.a = state.uiAlpha;

            Vec3 pos = planets.cam.project(Tmp.v31.set(hovered.tile.v).setLength(PlanetRenderer.outlineRad * state.planet.radius).rotate(Vec3.Y, -state.planet.getRotation()).add(state.planet.position));
            hoverLabel.setPosition(pos.x - Core.scene.marginLeft, pos.y - Core.scene.marginBottom, Align.center);

            hoverLabel.getText().setLength(0);
            if(hovered != null){
                StringBuilder tx = hoverLabel.getText();
                if(!canSelect(hovered)){
                    if(mode == planetLaunch){
                        tx.append("[gray]").append(Iconc.cancel);
                    }else{
                        tx.append("[gray]").append(Iconc.lock).append(" ").append(Core.bundle.get("locked"));
                    }
                }else{
                    tx.append("[accent][[ [white]").append(hovered.name()).append("[accent] ]");
                }
            }
            hoverLabel.invalidateHierarchy();
        }else{
            hoverLabel.remove();
        }

        if(launching && selected != null){
            lookAt(selected, 0.1f);
        }

        if(showing()){
            Sector to = newPresets.peek();

            presetShow += Time.delta;

            lookAt(to, 0.11f);
            zoom = 0.75f;

            if(presetShow >= 20f && !showed && newPresets.size > 1){
                showed = true;
                ui.announce(Iconc.lockOpen + " [accent]" + to.name(), 2f);
            }

            if(presetShow > sectorShowDuration){
                newPresets.pop();
                showed = false;
                presetShow = 0f;
            }
        }

        if(state.planet.hasGrid()){
            hovered = Core.scene.getDialog() == this ? state.planet.getSector(planets.cam.getMouseRay(), PlanetRenderer.outlineRad * state.planet.radius) : null;
        }else if(state.planet.isLandable()){
            boolean wasNull = selected == null;
            //always have the first sector selected.
            //TODO better support for multiple sectors in gridless planets?
            hovered = selected = state.planet.sectors.first();

            //autoshow
            if(wasNull){
                updateSelected();
            }
        }else{
            hovered = selected = null;
        }

        state.zoom = Mathf.lerpDelta(state.zoom, zoom, 0.4f);
        state.uiAlpha = Mathf.lerpDelta(state.uiAlpha, Mathf.num(state.zoom < 1.9f), 0.1f);
    }
    void updateSelected(){
        Sector sector = selected;
        Table stable = sectorTop;

        if(sector == null){
            stable.clear();
            stable.visible = false;
            return;
        }
        stable.visible = true;

        float x = stable.getX(Align.center), y = stable.getY(Align.center);
        stable.clear();
        stable.background(Styles.black6);

        stable.table(title -> {
            title.add("[accent]" + sector.name()).padLeft(3);
            if(sector.preset == null){
                title.add().growX();

                title.button(Icon.pencilSmall, Styles.clearNonei, () -> {
                    ui.showTextInput("@sectors.rename", "@name", 20, sector.name(), v -> {
                        sector.setName(v);
                        updateSelected();
                        rebuildList();
                    });
                }).size(40f).padLeft(4);
            }

            var icon = sector.info.contentIcon != null ?
                    new TextureRegionDrawable(sector.info.contentIcon.uiIcon) :
                    Icon.icons.get(sector.info.icon + "Small");

            title.button(icon == null ? Icon.noneSmall : icon, Styles.clearNonei, iconSmall, () -> {
                new Dialog(""){{
                    closeOnBack();
                    setFillParent(true);

                    Runnable refresh = () -> {
                        sector.saveInfo();
                        hide();
                        updateSelected();
                        rebuildList();
                    };

                    cont.pane(t -> {
                        resized(true, () -> {
                            t.clearChildren();
                            t.marginRight(19f);
                            t.defaults().size(48f);

                            t.button(Icon.none, Styles.squareTogglei, () -> {
                                sector.info.icon = null;
                                sector.info.contentIcon = null;
                                refresh.run();
                            }).checked(sector.info.icon == null && sector.info.contentIcon == null);

                            int cols = (int)Math.min(20, Core.graphics.getWidth() / Scl.scl(52f));

                            int i = 1;
                            for(var key : defaultIcons){
                                var value = Icon.icons.get(key);

                                t.button(value, Styles.squareTogglei, () -> {
                                    sector.info.icon = key;
                                    sector.info.contentIcon = null;
                                    refresh.run();
                                }).checked(key.equals(sector.info.icon));

                                if(++i % cols == 0) t.row();
                            }

                            for(ContentType ctype : defaultContentIcons){
                                t.row();
                                t.image().colspan(cols).growX().width(Float.NEGATIVE_INFINITY).height(3f).color(Pal.accent);
                                t.row();

                                i = 0;
                                for(UnlockableContent u : content.getBy(ctype).<UnlockableContent>as()){
                                    if(!u.isHidden() && u.unlocked()){
                                        t.button(new TextureRegionDrawable(u.uiIcon), Styles.squareTogglei, iconMed, () -> {
                                            sector.info.icon = null;
                                            sector.info.contentIcon = u;
                                            refresh.run();
                                        }).checked(sector.info.contentIcon == u);

                                        if(++i % cols == 0) t.row();
                                    }
                                }
                            }
                        });
                    });
                    buttons.button("@back", Icon.left, this::hide).size(210f, 64f);
                }}.show();
            }).size(40f).tooltip("@sector.changeicon");
        }).row();

        stable.image().color(Pal.accent).fillX().height(3f).pad(3f).row();

        boolean locked = sector.preset != null && sector.preset.locked() && !sector.hasBase() && sector.preset.techNode != null;

        if(locked){
            stable.table(r -> {
                r.add("@complete").colspan(2).left();
                r.row();
                for(Objectives.Objective o : sector.preset.techNode.objectives){
                    if(o.complete()) continue;

                    r.add("> " + o.display()).color(Color.lightGray).left();
                    r.image(o.complete() ? Icon.ok : Icon.cancel, o.complete() ? Color.lightGray : Color.scarlet).padLeft(3);
                    r.row();
                }
            }).row();
        }else if(!sector.hasBase()){
            float step = 0.25f;
            String color = Tmp.c1.set(Color.white).lerp(Color.scarlet, Mathf.round(sector.threat, step)).toString();
            String[] threats = ShaYeBuShi.threats;
            int index = Math.min((int)(sector.threat / step), threats.length - 1);
            String out = "[#" + color + "]" + Core.bundle.get("threat." + threats[index]);
            //System.out.println(out + " " + sector.threat);
            stable.add(Core.bundle.get("sectors.threat") + " [accent]" + out).row();
        }

        if(sector.isAttacked()){
            addSurvivedInfo(sector, stable, false);
        }else if(sector.hasBase() && sector.planet.allowSectorInvasion && sector.near().contains(Sector::hasEnemyBase)){
            stable.add("@sectors.vulnerable");
            stable.row();
        }else if(!sector.hasBase() && sector.hasEnemyBase()){
            stable.add("@sectors.enemybase");
            stable.row();
        }

        if(sector.save != null && sector.info.resources.any()){
            stable.table(t -> {
                t.add("@sectors.resources").padRight(4);
                for(UnlockableContent c : sector.info.resources){
                    if(c == null) continue; //apparently this is possible.
                    t.image(c.uiIcon).padRight(3).scaling(Scaling.fit).size(iconSmall);
                }
            }).padLeft(10f).fillX().row();
        }

        stable.row();

        if(sector.hasBase()){
            stable.button("@stats", Icon.info, Styles.cleart, () -> showStats(sector)).height(40f).fillX().row();
        }

        if((sector.hasBase() && mode == look) || canSelect(sector) || (sector.preset != null && sector.preset.alwaysUnlocked) || debugSelect){
            stable.button(
                    mode == select ? "@sectors.select" :
                            sector.isBeingPlayed() ? "@sectors.resume" :
                                    sector.hasBase() ? "@sectors.go" :
                                            locked ? "@locked" : "@sectors.launch",
                    locked ? Icon.lock : Icon.play, this::playSelected).growX().height(54f).minWidth(170f).padTop(4).disabled(locked);
        }

        stable.pack();
        stable.setPosition(x, y, Align.center);

        stable.act(0f);
    }
    public boolean canSelect(Sector sector){
        if (Version.build == -1 || ShaYeBuShi.tiaoshi) return true ;
        if(mode == select) return sector.hasBase() && launchSector != null /*&& sector.planet == launchSector.planet*/;
        //cannot launch to existing sector w/ accelerator TODO test
        if(mode == planetLaunch) return sector.id == sector.planet.startSector;
        if(sector.hasBase() || sector.id == sector.planet.startSector) return true;
        //preset sectors can only be selected once unlocked
        if(sector.preset != null){
            TechTree.TechNode node = sector.preset.techNode;
            return node == null || node.parent == null || (node.parent.content.unlocked() && (!(node.parent.content instanceof SectorPreset preset) || preset.sector.hasBase()));
        }

        return sector.planet.generator != null ?
                //use planet impl when possible
                sector.planet.generator.allowLanding(sector) :
                sector.hasBase() || sector.near().contains(Sector::hasBase); //near an occupied sector
    }
    public Sector findLauncher(Sector to){
        Sector launchSector = this.launchSector != null && this.launchSector.planet == to.planet && this.launchSector.hasBase() ? this.launchSector : null;
        //directly nearby.
        if(to.near().contains(launchSector)) return launchSector;

        Sector launchFrom = launchSector;
        if(launchFrom == null || (to.preset == null && !to.near().contains(launchSector))){
            //TODO pick one with the most resources
            launchFrom = to.near().find(Sector::hasBase);
            if(launchFrom == null && to.preset != null){
                if(launchSector != null) return launchSector;
                launchFrom = state.planet.sectors.min(s -> !s.hasBase() ? Float.MAX_VALUE : s.tile.v.dst2(to.tile.v));
                if(!launchFrom.hasBase()) launchFrom = null;
            }
        }

        return launchFrom;
    }
    public boolean showing(){
        return newPresets.any();
    }
    public void playSelected(){
        if(selected == null) return;

        Sector sector = selected;

        if(sector.isBeingPlayed()){
            //already at this sector
            hide();
            return;
        }

        if(sector.preset != null && sector.preset.locked() && sector.preset.techNode != null && !sector.hasBase()){
            return;
        }

        //make sure there are no under-attack sectors (other than this one)
        for(Planet planet : content.planets()){
            if(!planet.allowWaveSimulation && !debugSelect && planet.allowWaveSimulation == sector.planet.allowWaveSimulation){
                //if there are two or more attacked sectors... something went wrong, don't show the dialog to prevent softlock
                Sector attacked = planet.sectors.find(s -> s.isAttacked() && s != sector);
                if(attacked != null &&  planet.sectors.count(s -> s.isAttacked()) < 2){
                    BaseDialog dialog = new BaseDialog("@sector.noswitch.title");
                    dialog.cont.add(bundle.format("sector.noswitch", attacked.name(), attacked.planet.localizedName)).width(400f).labelAlign(Align.center).center().wrap();
                    dialog.addCloseButton();
                    dialog.buttons.button("@sector.view", Icon.eyeSmall, () -> {
                        dialog.hide();
                        lookAt(attacked);
                        selectSector(attacked);
                    });
                    dialog.show();

                    return;
                }
            }
        }

        boolean shouldHide = true;

        //save before launch.
        if(control.saves.getCurrent() != null && Vars.state.isGame() && mode != select){
            try{
                control.saves.getCurrent().save();
            }catch(Throwable e){
                e.printStackTrace();
                ui.showException("[accent]" + Core.bundle.get("savefail"), e);
            }
        }

        if(mode == look && !sector.hasBase()){
            shouldHide = false;
            Sector from = findLauncher(sector);

            if(from == null){
                //clear loadout information, so only the basic loadout gets used
                universe.clearLoadoutInfo();
                //free launch.
                control.playSector(sector);
            }else{
                CoreBlock block = sector.allowLaunchSchematics() ? (from.info.bestCoreType instanceof CoreBlock b ? b : (CoreBlock)from.planet.defaultCore) : (CoreBlock)from.planet.defaultCore;

                loadouts.show(block, from, sector, () -> {
                    var loadout = universe.getLastLoadout();
                    var schemCore = loadout.findCore();
                    from.removeItems(loadout.requirements());
                    from.removeItems(universe.getLaunchResources());

                    Events.fire(new EventType.SectorLaunchLoadoutEvent(sector, from, loadout));

                    if(settings.getBool("skipcoreanimation")){
                        //just... go there
                        control.playSector(from, sector);
                        //hide only after load screen is shown
                        Time.runTask(8f, this::hide);
                    }else{
                        //hide immediately so launch sector is visible
                        hide();

                        //allow planet dialog to finish hiding before actually launching
                        Time.runTask(5f, () -> {
                            Runnable doLaunch = () -> {
                                renderer.showLaunch(schemCore);
                                //run with less delay, as the loading animation is delayed by several frames
                                Time.runTask(coreLandDuration - 8f, () -> control.playSector(from, sector));
                            };

                            //load launchFrom sector right before launching so animation is correct
                            if(!from.isBeingPlayed()){
                                //run *after* the loading animation is done
                                Time.runTask(9f, doLaunch);
                                control.playSector(from);
                            }else{
                                doLaunch.run();
                            }
                        });
                    }
                });
            }
        }else if(mode == select){
            listener.get(sector);
        }else if(mode == planetLaunch){ //TODO make sure it doesn't have a base already.

            //TODO animation
            //schematic selection and cost handled by listener
            listener.get(sector);
            //unlock right before launch
            sector.planet.unlockedOnLand.each(UnlockableContent::unlock);
            control.playSector(sector);
        }else{
            //sector should have base here
            control.playSector(sector);
        }

        if(shouldHide) hide();
    }
    public boolean selectable(Planet planet){
        //TODO what if any sector is selectable?
        //TODO launch criteria - which planets can be launched to? Where should this be defined? Should planets even be selectable?
        //if (Version.build == -1) return true ;
        if(mode == select) return /*planet == state.planet*/true;
        if(mode == planetLaunch) return launchSector != null && planet != launchSector.planet && launchSector.planet.launchCandidates.contains(planet);
        return (planet.alwaysUnlocked && planet.isLandable()) || planet.sectors.contains(Sector::hasBase) || debugSelect;
    }
    public void rebuildExpand(){
        Table c = expandTable;
        c.clear();
        c.visible(() -> !(graphics.isPortrait() && mobile));
        if(state.planet.sectors.contains(Sector::hasBase)){
            int attacked = state.planet.sectors.count(Sector::isAttacked);

            //sector notifications & search
            c.top().right();
            c.defaults().width(290f);

            c.button(bundle.get("sectorlist") +
                            (attacked == 0 ? "" : "\n[red]⚠[lightgray] " + bundle.format("sectorlist.attacked", "[red]" + attacked + "[]")),
                    Icon.downOpen, Styles.squareTogglet, () -> sectorsShown = !sectorsShown)
                    .height(60f).checked(b -> {
                Image image = (Image)b.getCells().first().get();
                image.setDrawable(sectorsShown ? Icon.upOpen : Icon.downOpen);
                return sectorsShown;
            }).with(t -> t.left().margin(7f)).with(t -> t.getLabelCell().grow().left()).row();

            c.collapser(t -> {
                t.background(Styles.black8);

                notifs = t;
                rebuildList();
            }, false, () -> sectorsShown).padBottom(64f).row();
        }
    }
    public void rebuildList(){
        if(notifs == null) return;

        notifs.clear();

        var all = state.planet.sectors.select(Sector::hasBase);
        all.sort(Structs.comps(Structs.comparingBool(s -> !s.isAttacked()), Structs.comparingInt(s -> s.save == null ? 0 : -(int)s.save.meta.timePlayed)));

        notifs.pane(p -> {
            Runnable[] readd = {null};

            p.table(s -> {
                s.image(Icon.zoom).padRight(4);
                s.field(searchText, t -> {
                    searchText = t;
                    readd[0].run();
                }).growX().height(50f);
            }).growX().row();

            Table con = p.table().growX().get();
            con.touchable = Touchable.enabled;

            readd[0] = () -> {
                con.clearChildren();
                for(Sector sec : all){
                    if(sec.hasBase() && (searchText.isEmpty() || sec.name().toLowerCase().contains(searchText.toLowerCase()))){
                        con.button(t -> {
                            t.marginRight(10f);
                            t.left();
                            t.defaults().growX();

                            t.table(head -> {
                                head.left().defaults();

                                if(sec.isAttacked()){
                                    head.image(Icon.warningSmall).update(i -> {
                                        i.color.set(Pal.accent).lerp(Pal.remove, Mathf.absin(Time.globalTime, 9f, 1f));
                                    }).padRight(4f);
                                }

                                String ic = sec.iconChar() == null ? "" : sec.iconChar() + " ";

                                head.add(ic + sec.name()).growX().wrap();
                            }).growX().row();

                            if(sec.isAttacked()){
                                addSurvivedInfo(sec, t, true);
                            }
                        }, Styles.underlineb, () -> {
                            lookAt(sec);
                            selected = sec;
                            updateSelected();
                        }).margin(8f).marginLeft(13f).marginBottom(6f).marginTop(6f).padBottom(3f).padTop(3f).growX().checked(b -> selected == sec).row();
                        //for resources: .tooltip(sec.info.resources.toString("", u -> u.emoji()))
                    }
                }

                if(con.getChildren().isEmpty()){
                    con.add("@none.found").pad(10f);
                }
            };

            readd[0].run();
        }).grow().scrollX(false);
    }
    public void addSurvivedInfo(Sector sector, Table table, boolean wrap){
        if(!wrap){
            table.add(sector.planet.allowWaveSimulation ? Core.bundle.format("sectors.underattack", (int)(sector.info.damage * 100)) : "@sectors.underattack.nodamage").wrapLabel(wrap).row();
        }

        if(sector.planet.allowWaveSimulation && sector.info.wavesSurvived >= 0 && sector.info.wavesSurvived - sector.info.wavesPassed >= 0 && !sector.isBeingPlayed()){
            int toCapture = sector.info.attack || sector.info.winWave <= 1 ? -1 : sector.info.winWave - (sector.info.wave + sector.info.wavesPassed);
            boolean plus = (sector.info.wavesSurvived - sector.info.wavesPassed) >= SectorDamage.maxRetWave - 1;
            table.add(Core.bundle.format("sectors.survives", Math.min(sector.info.wavesSurvived - sector.info.wavesPassed, toCapture <= 0 ? 200 : toCapture) +
                    (plus ? "+" : "") + (toCapture < 0 ? "" : "/" + toCapture))).wrapLabel(wrap).row();
        }
    }
    public void showStats(Sector sector){
        BaseDialog dialog = new BaseDialog(sector.name());

        dialog.cont.pane(c -> {
            c.defaults().padBottom(5);

            if(sector.preset != null && sector.preset.description != null){
                c.add(sector.preset.displayDescription()).width(420f).wrap().left().row();
            }

            c.add(Core.bundle.get("sectors.time") + " [accent]" + sector.save.getPlayTime()).left().row();

            if(sector.info.waves && sector.hasBase()){
                c.add(Core.bundle.get("sectors.wave") + " [accent]" + (sector.info.wave + sector.info.wavesPassed)).left().row();
            }

            if(sector.isAttacked() || !sector.hasBase()){
                float step = 0.25f;
                String color = Tmp.c1.set(Color.white).lerp(Color.scarlet, Mathf.round(sector.threat, step)).toString();
                String[] threats = ShaYeBuShi.threats;
                int index = Math.min((int)(sector.threat / step), threats.length - 1);
                String out = "[#" + color + "]" + Core.bundle.get("threat." + threats[index]);
                c.add(Core.bundle.get("sectors.threat") + " [accent]" + out).left().row();
            }

            if(sector.save != null && sector.info.resources.any()){
                c.add("@sectors.resources").left().row();
                c.table(t -> {
                    for(UnlockableContent uc : sector.info.resources){
                        if(uc == null) continue;
                        t.image(uc.uiIcon).scaling(Scaling.fit).padRight(3).size(iconSmall);
                    }
                }).padLeft(10f).left().row();
            }

            //production
            displayItems(c, sector.getProductionScale(), sector.info.production, "@sectors.production");

            //export
            displayItems(c, sector.getProductionScale(), sector.info.export, "@sectors.export", t -> {
                if(sector.info.destination != null && sector.info.destination.hasBase()){
                    String ic = sector.info.destination.iconChar();
                    t.add(Iconc.rightOpen + " " + (ic == null || ic.isEmpty() ? "" : ic + " ") + sector.info.destination.name()).padLeft(10f).row();
                }
            });

            //import
            if(sector.hasBase()){
                displayItems(c, 1f, sector.info.importStats(sector.planet), "@sectors.import", t -> {
                    sector.info.eachImport(sector.planet, other -> {
                        String ic = other.iconChar();
                        t.add(Iconc.rightOpen + " " + (ic == null || ic.isEmpty() ? "" : ic + " ") + other.name()).padLeft(10f).row();
                    });
                });
            }

            ItemSeq items = sector.items();

            //stored resources
            if(sector.hasBase() && items.total > 0){

                c.add("@sectors.stored").left().row();
                c.table(t -> {
                    t.left();

                    t.table(res -> {

                        int i = 0;
                        for(ItemStack stack : items){
                            res.image(stack.item.uiIcon).padRight(3);
                            res.add(UI.formatAmount(Math.max(stack.amount, 0))).color(Color.lightGray);
                            if(++i % 4 == 0){
                                res.row();
                            }
                        }
                    }).padLeft(10f);
                }).left().row();
            }
        });

        dialog.addCloseButton();

        if(sector.hasBase()){
            dialog.buttons.button("@sector.abandon", Icon.cancel, () -> abandonSectorConfirm(sector, dialog::hide));
        }

        dialog.show();
    }
    public void displayItems(Table c, float scl, ObjectMap<Item, SectorInfo.ExportStat> stats, String name){
        displayItems(c, scl, stats, name, t -> {});
    }

    public void displayItems(Table c, float scl, ObjectMap<Item, SectorInfo.ExportStat> stats, String name, Cons<Table> builder){
        Table t = new Table().left();

        int i = 0;
        for(var item : content.items()){
            var stat = stats.get(item);
            if(stat == null) continue;
            int total = (int)(stat.mean * 60 * scl);
            if(total > 1){
                t.image(item.uiIcon).padRight(3);
                t.add(UI.formatAmount(total) + " " + Core.bundle.get("unit.perminute")).color(Color.lightGray).padRight(3);
                if(++i % 3 == 0){
                    t.row();
                }
            }
        }

        if(t.getChildren().any()){
            c.defaults().left();
            c.add(name).row();
            builder.get(c);
            c.add(t).padLeft(10f).row();
        }
    }
    public void lookAt(Sector sector){
        if(sector.tile == PlanetGrid.Ptile.empty) return;

        state.planet = sector.planet;
        state.camPos.set(Tmp.v33.set(sector.tile.v).rotate(Vec3.Y, -sector.planet.getRotation()));
    }
    public void selectSector(Sector sector){
        selected = sector;
        updateSelected();
    }
}
