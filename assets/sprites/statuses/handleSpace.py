from PIL import Image as im 
target = "denglizihua.png"
I = im.open(target)
I = I.convert("RGBA")
II = I.load()
for i in range(I.size[0]):
    for x in range(I.size[1]):
        III,IV,V,VI = II[i,x]
        if (III >= 220 and IV >= 220 and V >= 220):
            II[i,x] = (III,IV,V,0)
        # if (VI != 0) :
        #     II[i,x] = (76,143,58,255)
I.save(target)
