	var recentActivity = "-";
	
	var settings = {"hintTime": 20000};
	var hintGiven = false;
	var currentStage = 0;
	var trailDone = false
	function checkMilestones(){
		
		var mileStoneFinished = false;
		if( recentActivity == this.mileStones[currentStage]) {
			mileStoneFinished = true;
		} 
		return mileStoneFinished;
	}
	
	
	
	
