Plugin simpleObject NewCylinder
name: "New Cylinder"
category: "MyObjects"
classID: #(0x41fc75c6, 0x55236495)
(
	parameters main rollout: params
	(
		radius type: #float ui: radius default:5
		height type: #float ui: height default:10
		sides type: #integer ui: sides default:10
		segments type: #integer ui: segments default:5
	)
	
	rollout params "New Cylinder"
	(
		spinner radius "Radius" range: [0, 10E7, 0]
		spinner height "Height" range: [-10E7, 10E7, 0]		
		spinner sides "Sides" range: [3, 100, 10] type: #integer
		spinner segments "Segments" range: [1, 100, 5] type: #integer
	)
	on buildMesh do
	(
		vertArray = #()
		faceArray = #()
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
		--m = mesh vertices: vertArray faces: faceArray		
		setMesh mesh verts: vertArray faces: faceArray
	)-- end buildMesh
	tool create
	(
		on mousePoint click do
		(
			case click of
			(
				1: coordsys grid(nodeTM.translation = gridPoint)
			)
		)
		on mouseMove click do
		(
			case click of
			(
				2: (radius = abs(gridDist.x))
				3: (height = abs(gridDist.y))
				4: (#stop)
			)
		)
	)--end tool
)-- end Plugin