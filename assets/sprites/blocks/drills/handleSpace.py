from PIL import Image as im 
I = im.open("duogongnengzuantou-rim.png")
I = I.convert("RGBA")
II = I.load()
for i in range(I.size[0]):
    for x in range(I.size[1]):
        III,IV,V,VI = II[i,x]
        if (III >= 200 and IV >= 200 and V >= 200):
            II[i,x] = (III,IV,V,0)
I.save("duogongnengzuantou-rim.png")