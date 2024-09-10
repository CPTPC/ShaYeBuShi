from PIL import Image as im 
tar = "daxingdianjieji.png"
I = im.open(tar)
I = I.convert("RGBA")
II = I.load()
for i in range(I.size[0]):
    for x in range(I.size[1]):
        III,IV,V,VI = II[i,x]
        if (III <= 40 and IV <= 40 and V <= 40):
            II[i,x] = (III,IV,V,0)
        # if (VI != 0):
        #     II[i,x] = (III,IV,V,0)
        # else :
        #     II[i,x] = (255,211,107,255)
I.save(tar)
