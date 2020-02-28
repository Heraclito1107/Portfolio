
--Imagen Final del Render

Fn CreateCams targetObj =
(
	try (delete $Holocam*) catch()
	camDist = (length(targetObj.max - targetObj.min))*2	--Float (Distance)
	camTargetPos = (targetObj.max + targetObj.min)/2	--Vector 3 (Pos)
	camHeight = targetObj.max.z


	camFrontPos = [camTargetPos.x,camTargetPos.y-camDist,camHeight]
	camBackPos = [camTargetPos.x, camTargetPos.y+camDist,camHeight]
	camLeftPos = [camTargetPos.x-camDist,camTargetPos.y,camHeight]
	camRightPos = [camTargetPos.x+camDist,camTargetPos.y,camHeight]


	global frontCam =Targetcamera name:"HolocamFront" wirecolor:green pos:camFrontPos target:(Targetobject name:"TargetFront" transform:(matrix3 [1,0,0] [0,1,0] [0,0,1] camTargetPos))
	global backCam =Targetcamera name:"HolocamBack" wirecolor:green pos:camBackPos target:(Targetobject name:"TargetBack" transform:(matrix3 [1,0,0] [0,1,0] [0,0,1] camTargetPos))
	global leftCam =Targetcamera name:"HolocamLeft" wirecolor:green pos:camLeftPos target:(Targetobject name:"TargetLeft" transform:(matrix3 [1,0,0] [0,1,0] [0,0,1] camTargetPos))
	global rightCam =Targetcamera name:"HolocamRight" wirecolor:green pos:camRightPos target:(Targetobject name:"TargetRight" transform:(matrix3 [1,0,0] [0,1,0] [0,0,1] camTargetPos))

	returnValues=#(CamDist,camHeight)		
		
)

Fn Mirror2DArray arrayToMirror = (
		
		newMatrix = #()
		for i in 1 to arrayToMirror.count do(
			
			newMatrix[arrayToMirror.count-i+1] = arrayToMirror[i]
			)
			newMatrix
		
		)
		
Fn Rotar2DArray180 theBmp =(
	/*
	w = theBmp.width
	h = theBmp.height
	
	bmpMatrix = #()
	
	for j in 1 to a do
	(
		bmpMatrix[j]= getPixels theBmp [0, j-1] w linear:true
	)
	
	temp = #()
	
	for i in 1 to h+1
	(
		c = bmpMatrix[w, i]
		bmpMatrix[w, i] = temp
		temp	= c
		
		top = top+1
	)
	
	for i in 1 to w+1
	(
		c = bmpMatrix[i, h]
		bmpMatrix[i, h] = temp*/
		
	
	)
		
		
Fn RenderCamera cam pxWidth pxHeight progBar prevBitmap imRotation= (
	progBar.value = 15
	camRender = render camera:cam outputwidth:pxWidth outputheight:pxHeight vfb:false
	
	camRenderMatrix = #()
	for h in 1 to pxHeight do
	(
		camRenderMatrix[h]= getPixels camRender [0, h-1] pxWidth linear:true
		)
	
	progBar.value = 60
	
	camRenderMatrix = Mirror2DArray camRenderMatrix
		
	tempBitmap = bitmap pxWidth pxHeight color:black
	newBitmap = bitmap pxWidth pxHeight color:black
	
	for h in 1 to pxHeight do
	(
		setPixels tempBitmap [0, h-1] camRenderMatrix[h]
	)
	
	if (imRotation == 1) then
	(
		for i = 1 to pxHeight do
		(
			for j = 1 to pxWidth do
			(
				pasteBitmap tempBitmap newBitmap [(pxWidth-i+1), (pxWidth-j+1)] [i, j]
			)
		)
	)
	else if (imRotation == 2) then
	(
		for i = 1 to pxHeight do
		(
			for j = 1 to pxWidth do
			(
				pasteBitmap tempBitmap newBitmap [j, i] [i, j]
			)
		)
	)
	else if (imRotation == 3) then
	(
		for i = 1 to pxHeight do
		(
			for j = 1 to pxWidth do
			(
				pasteBitmap tempBitmap newBitmap [i, (pxWidth-j+1)] [i, j]
			)
		)
	)
	else
	(
		copy tempBitmap newBitmap	
	)
	-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
		
	prevBmp = bitmap 75 75 color: black
	copy newBitmap prevBmp
	prevBitmap.bitmap = prevBmp
	progBar.value =100
	newBitmap 
	
		
		
		
	)
	
Try(DestroyDialog HoloPyramid_Rollout)catch()

rollout HoloPyramid_Rollout "Holo-pyramid" width:600 height:457
(
	bitmap 				'Principal_bmp' 	"" 		pos:[233,15] 	width:326 	height:250 	align:#left
	bitmap 				'Down_bmp' 		"" 		pos:[320,325] width:75 	height:75 	align:#left
	bitmap 				'Right_bmp' 		"" 		pos:[401,325] width:75 	height:75 	align:#left
	bitmap 				'Left_bmp' 			"" 		pos:[484,325] width:75 	height:75 	align:#left
	bitmap 				'Up_bmp' 			"" 		pos:[232,325] width:75 	height:75 	align:#left
	
	progressBar 		'Principal_pb' 		"" 		pos:[233,265] width:326 	height:20 color:(color 30 10 190) align:#left
	progressBar 		'Up_pb' 				"" 		pos:[232,400] width:75 	height:15 color:(color 30 10 190) align:#left
	progressBar 		'Down_pb' 			"" 		pos:[320,400] width:75 	height:15 color:(color 30 10 190) align:#left
	progressBar 		'Right_pb' 			"" 		pos:[401,400] width:75 	height:15 color:(color 30 10 190) align:#left
	progressBar 		'Left_pb' 			"" 		pos:[484,400] width:75 	height:15 color:(color 30 10 190) align:#left

	pickButton 		'Obj_btn' 			"Select Object" 		pos:[42,15] 	width:150 	height:31 align:#left	
	
	spinner				'Distance_spn' 	"Camera Distance  " 	pos:[42,58] 	width:63 	height:16 enabled:false range:[0.001,1000000,0] align:#left
	spinner 			'HeightCam_spn' 	"Camera Height  " 	pos:[42,80] 	width:74 	height:16 enabled:false range:[-100000,100000,0] align:#left
	spinner 			'Start_spn' 			"Start Frame  " 		pos:[42,188] 	width:90	height:16 enabled:false range:[0,99,0] 		type:#integer scale:1 align:#left
	spinner 			'End_spn' 			"End Frame  " 			pos:[42,210] 	width:93 	height:16 enabled:false range:[1,100,100] 	type:#integer scale:1 align:#left
	spinner 			'Width_spn' 		"Width" 						pos:[42,339] 	width:122 	height:16 enabled:false range:[1,2000,0] 	type:#integer scale:1 align:#left
	spinner 			'Height_spn' 		"Height" 						pos:[42,361] 	width:119 	height:16 enabled:false range:[0,100,0] 		type:#integer scale:1 align:#left
	
	dropDownList 	'Device_ddl' 		"Device" 				pos:[42,246] 	width:150 	height:40 enabled:false align:#left

	
	button 				'Cam_btn' 			"Create Cameras" 	pos:[42,113] 	width:150 	height:31 enabled:false align:#left
	button 				'Render_btn' 		"Render" 					pos:[42,403] 	width:150 	height:31 enabled:false align:#left
	
	checkbox 			'Resolution_chk' 	"Customize Resolution" 	pos:[42,295] 	width:150 	height:15 enabled:false align:#left
	groupBox 			'Preview_grp' 		"Render Preview"	 		pos:[210,301] width:373 	height:142 align:#left
	
	global selectObj =undefined
	
	on Obj_btn picked obj do
	(
			if obj!=undefined then
				(
					Obj_btn.caption=obj.name
					Cam_btn.enabled=true
				)
		
		
		)
	
	on  Cam_btn pressed do	
	(
		try (delete $Holocam*) catch()
		
		TargetObj=Obj_btn.object
		distValues=CreateCams targetObj
		
		Distance_spn.enabled=true
		Distance_spn.value=distValues[1]
		
		HeightCam_spn.enabled=true
		HeightCam_spn.value=distValues[2]
		
		Render_btn.enabled=true

	)

	on Distance_spn changed distVal do
	(
		frontCam.pos = ((normalize(frontCam.pos-frontCam.target.pos))*distVal)+ frontCam.target.pos
		backCam.pos = ((normalize(backCam.pos-backCam.target.pos))*distVal) + backCam.target.pos
		leftCam.pos = ((normalize(leftCam.pos-leftCam.target.pos))*distVal) + leftCam.target.pos
		rightCam.pos = ((normalize(rightCam.pos-rightCam.target.pos))*distVal) + rightCam.target.pos
	
		HeightCam_spn.value=frontCam.pos.z
	)
	
	on HeightCam_spn changed HeightVal do
	(
		frontcam.pos.z = HeightVal
		backcam.pos.z = HeightVal
		leftcam.pos.z = HeightVal
		rightcam.pos.z = HeightVal
	
		Distance_spn.value=length (frontCam.pos-frontCam.target.pos)
	)
	
	on Render_btn pressed do(
		bigImgW = 1920
		bigImgH = 1080
		smImgRes = bigImgH/3
		
		frontRender = RenderCamera frontCam smImgRes smImgRes Up_pb Up_bmp 0
		Principal_pb.value = 10
		backRender = RenderCamera backCam smImgRes smImgRes Down_pb Down_bmp 1
		Principal_pb.value = 20
		leftRender = RenderCamera leftCam smImgRes smImgRes Left_pb Left_bmp 2 
		Principal_pb.value = 30
		rightRender = RenderCamera rightCam smImgRes smImgRes Right_pb Right_bmp 3
		Principal_pb.value = 40
		
	
		/*prevCompBitmap = bitmap bigImgW bigImgH color:black
		
		frontCoordX = [smImgRes*2, ]*/
		)
	
)

CreateDialog HoloPyramid_Rollout