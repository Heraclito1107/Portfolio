try(delete $Object001)catch()
vertArray = #()
faceArray = #()

sides = 20
height = 50
baseRadius = 10
radiusOffset = 3
segments =20
loops = 10
segStep = ((height*1.000)/segments)
angleStep = 360.00/sides
verticalArgument = (((2*loops)-1)*180.00/height)*segStep
append vertArray [0, 0, 0]
k = 1
for j= 0 to segments  do
(
	radius = baseRadius + radiusOffset*(sin((j*verticalArgument)))
	for i = 1 to sides do
	(
		append vertArray [radius*cos(i*angleStep), radius*sin((i*angleStep)), segStep*j]
	)
)

append vertArray [0, 0, height]

for i = 2 to sides do
(
	append faceArray [i, 1, i+1]
	append faceArray [((segments*sides)+i), ((segments*sides)+i+1), ((segments+1)*(sides)+2)]
)


append faceArray [sides+1, 1, 2]
append faceArray [((segments+1)*sides)+1, ((segments)*sides)+2, ((segments+1)*(sides)+2)]

for j = 0 to segments-1 do
(
	for i = 2 to sides do
	(
		append faceArray [(j*sides)+i+1, (j+1)*sides+i, (j*sides)+i]
		append faceArray [(j*sides)+i+1, (j+1)*sides+i+1, (j+1)*sides+i]
	)

	append faceArray [(j*sides+2), ((j+2)*sides+1), (j+1)*sides+1]
	append faceArray [(j*sides+2), ((j+1)*sides+2) , (j+2)*sides+1]
)
m = mesh vertices: vertArray faces: faceArray

select $Object001 
modPanel.addModToSelection(smooth())
macros.run "Modifier Stack" "Convert_to_Mesh"