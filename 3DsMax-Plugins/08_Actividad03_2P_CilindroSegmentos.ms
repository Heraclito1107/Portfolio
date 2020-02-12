try(delete $Object001)catch()
vertArray = #()
faceArray = #()

sides = 16
height = 20
radius = 5
segments = 10
segStep = ((height*1.00)/segments)
angleStep = 360.00/sides

append vertArray [0, 0, 0]
k = 1
for j= 0 to segments  do
(
	for i = 1 to sides do
	(
		append vertArray [radius*cos(i*angleStep), radius*sin(i*angleStep), segStep*j]
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