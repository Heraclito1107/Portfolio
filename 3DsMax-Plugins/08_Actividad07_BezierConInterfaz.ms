Fn AnimateJump jumper jumpStart jumpEnd jumpHeight startFrame endFrame = 
(
	DeleteKeys jumper #allKeys
	
	frameNum = endFrame - startFrame
	
	midVector = jumpEnd - jumpStart
	midDistance = (length midVector) / 2
	
	controlPoint_02 = ((normalize midVector) * midDistance) + jumpStart
	controlPoint_02.z = jumpHeight  + jumpStart.z
	
	vector_01 = controlPoint_02 - jumpStart
	vector_02 = jumpEnd - controlPoint_02


	Animate on
		(
			for t = startFrame to endFrame do 
			(
				at time t
				(
					controlPoint_04 = (normalize vector_01) * (((length vector_01) / frameNum) * t) + jumpStart
					controlPoint_05 = (normalize vector_02) * (((length vector_02) / frameNum) * t) + controlPoint_02
					
					vector_03 = controlPoint_05 - controlPoint_04
					
					travelerPos = (normalize vector_03) * (((length vector_03) / frameNum) * t) + controlPoint_04
					
					jumper.pos = travelerPos
				)
			)
		)
)

AnimateJump $Traveler $CP_01.pos $CP_02.pos 100 0 100

Rollout JumpAnimator_Rollout "Jump Animator" width: 250 height: 250
(
	
)

CreateDialog JumpAnimator_Rollout