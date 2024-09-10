from PIL import Image as im 
target = "malign-mid.png"
I = im.open(target)
I = I.convert("RGBA")
II = I.load()
for i in range(I.size[0]):
    for x in range(I.size[1]):
        III,IV,V,VI = II[i,x]
        if (III == 255 and IV == 255 and V == 255):
            II[i,x] = (III,IV,V,0)
I.save(target)
