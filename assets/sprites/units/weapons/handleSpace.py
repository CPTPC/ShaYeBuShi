from PIL import Image as im 
target = "anyong-shuilei.png"
I = im.open(target)
I = I.convert("RGBA")
II = I.load()
for i in range(I.size[0]):
    for x in range(I.size[1]):
        III,IV,V,VI = II[i,x]
        if (III >= 200 and IV >= 200 and V >= 200):
            II[i,x] = (III,IV,V,0)
        # elif (III != 220 and IV != 198 and V != 198) :
        #     II[i,x] = (255,255,255,255)
I.save(target)
