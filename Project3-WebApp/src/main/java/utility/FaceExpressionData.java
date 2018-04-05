package utility;

public class FaceExpressionData {

	Double raiseBrow = 0.0;
	Double furrowBrow = 0.0;
	Double smile = 0.0;
	Double clench = 0.0;
	Double smirkLeft = 0.0;
	Double smirkRight = 0.0;
	Double laugh = 0.0;
	Double blink = 0.0;
	Double winkLeft = 0.0;
	Double winkRight = 0.0;
	Double lookLeft = 0.0;
	Double lookRight = 0.0;
	Boolean eyeReset = false;

	public FaceExpressionData() {
	}

	public Double getRaiseBrow() {
		return raiseBrow;
	}

	public void setRaiseBrow(Double raiseBrow) {
		this.raiseBrow = raiseBrow;
	}

	public Double getFurrowBrow() {
		return furrowBrow;
	}

	public void setFurrowBrow(Double furrowBrow) {
		this.furrowBrow = furrowBrow;
	}

	public Double getSmile() {
		return smile;
	}

	public void setSmile(Double smile) {
		this.smile = smile;
	}

	public Double getClench() {
		return clench;
	}

	public void setClench(Double clench) {
		this.clench = clench;
	}

	public Double getSmirkLeft() {
		return smirkLeft;
	}

	public void setSmirkLeft(Double smirkLeft) {
		this.smirkLeft = smirkLeft;
	}

	public Double getSmirkRight() {
		return smirkRight;
	}

	public void setSmirkRight(Double smirkRight) {
		this.smirkRight = smirkRight;
	}

	public Double getLaugh() {
		return laugh;
	}

	public void setLaugh(Double laugh) {
		this.laugh = laugh;
	}

	public Double getBlink() {
		return blink;
	}

	public void setBlink(Double blink) {
		this.blink = blink;
	}

	public Double getWinkLeft() {
		return winkLeft;
	}

	public void setWinkLeft(Double winkLeft) {
		this.winkLeft = winkLeft;
	}

	public Double getWinkRight() {
		return winkRight;
	}

	public void setWinkRight(Double winkRight) {
		this.winkRight = winkRight;
	}

	public Double getLookLeft() {
		return lookLeft;
	}

	public void setLookLeft(Double lookLeft) {
		this.lookLeft = lookLeft;
	}

	public Double getLookRight() {
		return lookRight;
	}

	public void setLookRight(Double lookRight) {
		this.lookRight = lookRight;
	}

	public Boolean getEyeReset() {
		return eyeReset;
	}

	public void setEyeReset(Boolean eyeReset) {
		this.eyeReset = eyeReset;
	}

	public double[] fetchVector() {
		// Double[] p = new Double[13];
		// p[1] = getRaiseBrow();
		// p[2] = getFurrowBrow();
		// p[3] = getSmile();
		// p[4] = getClench();
		// p[5] = getSmirkLeft();
		// p[6] = getSmirkRight();
		// p[7] = getLaugh();
		// p[8] = getBlink();
		// p[9] = getWinkLeft();
		// p[10] = getWinkRight();
		// p[11] = getLookLeft();
		// p[12] = getLookRight();
		double[] p = { 0.0345, 0.678, 0.467678, 0.6788, 0.567, 0.6788, 0.67868, 0.7989, 0.6878, 0.79879, 0.7898, 0.79,
				0.890 };

		return p;
	}

	@Override
	public String toString() {
		return "FaceExpressionData{" + "raiseBrow=" + raiseBrow + ", furrowBrow=" + furrowBrow + ", smile=" + smile
				+ ", clench=" + clench + ", smirkLeft=" + smirkLeft + ", smirkRight=" + smirkRight + ", laugh=" + laugh
				+ ", blink=" + blink + ", winkLeft=" + winkLeft + ", winkRight=" + winkRight + ", lookLeft=" + lookLeft
				+ ", lookRight=" + lookRight + ", eyeReset=" + eyeReset + '}';
	}

}
