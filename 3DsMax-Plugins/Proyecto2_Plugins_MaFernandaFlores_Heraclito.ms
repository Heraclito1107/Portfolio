sectNum = 20
maxB = $.max
minB = $.min
widthX = maxB.x - minB.x
depthY = maxB.y - minB.y
heightZ = maxB.z - minB.z
sections = #()
objectN = $
orientation = 2 --1 para horizontal, 2 para vertical

for t = 0 to sectNum-1 do
(
	intersection = instance objectN
	if (orientation < 2) then
	(
		sectPos = heightZ*(t+0.5)/sectNum
		currentSection = box height: (heightZ/(15*sectNum)) width: widthX length: depthY pos: [ minB.x + (widthX/2.00), minB.y + (depthY/2.00), minB.z+sectPos] 
	)
	else
	(
		sectPos = widthX*(t+0.5)/sectNum
		currentSection = box height: heightZ width: (widthX/(15*sectNum)) length: depthY pos: [ minB.x + sectPos, minB.y + (depthY/2.00), minB.z] 
	)
	ProBoolean.createBooleanObjects intersection currentSection 1 0 0
)
hide objectN