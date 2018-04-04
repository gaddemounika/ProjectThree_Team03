package utility;

import server.FaceAffectiveData;

public class FaceData {
	FaceExpressionData faceExpressionData;
	FaceAffectiveData faceAffectiveData;
	
	public FaceExpressionData getFaceExpressionData() {
		return faceExpressionData;
	}
	public void setFaceExpressionData(FaceExpressionData faceExpressionData) {
		this.faceExpressionData = faceExpressionData;
	}
	public FaceAffectiveData getFaceAffectiveData() {
		return faceAffectiveData;
	}
	public void setFaceAffectiveData(FaceAffectiveData faceAffectiveData) {
		this.faceAffectiveData = faceAffectiveData;
	}
	
	public String toString() {
		return "FaceData{" +
				"faceExpressionData=" + faceExpressionData +
				", faceAffectiveData=" + faceAffectiveData +
				'}';
	}
}
