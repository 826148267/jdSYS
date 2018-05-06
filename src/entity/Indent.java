package entity;

public class Indent {
	private int indentId;
	private int goodsId;
	private String clientId;
	private String uid;
	private int num;
	private int surplus;
	private String goodTime;
	private String indentTime;
	private String status;
	private String txtPath;
	private float ownerQQ;
	private float indentPrice;
	private float goodPrice;
	private String remark;
	private String corp;
	private String description;
	public float getIndentPrice() {
		return indentPrice;
	}
	public void setIndentPrice(float indentPrice) {
		this.indentPrice = indentPrice;
	}
	public float getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(float goodPrice) {
		this.goodPrice = goodPrice;
	}
	public int getSurplus() {
		return surplus;
	}
	public void setSurplus(int surplus) {
		this.surplus = surplus;
	}
	public String getCorp() {
		return corp;
	}
	public void setCorp(String corp) {
		this.corp = corp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGoodTime() {
		return goodTime;
	}
	public void setGoodTime(String goodTime) {
		this.goodTime = goodTime;
	}
	public String getIndentTime() {
		return indentTime;
	}
	public void setIndentTime(String indentTime) {
		this.indentTime = indentTime;
	}
	public int getIndentId() {
		return indentId;
	}
	public void setIndentId(int indentId) {
		this.indentId = indentId;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTxtPath() {
		return txtPath;
	}
	public void setTxtPath(String txtPath) {
		this.txtPath = txtPath;
	}
	public float getOwnerQQ() {
		return ownerQQ;
	}
	public void setOwnerQQ(float f) {
		this.ownerQQ = f;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
