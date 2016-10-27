package com.zendaimoney.crm.product.vo;

import com.zendaimoney.crm.product.util.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 */
public class ApplyMatchVo implements Serializable{

    private static final long serialVersionUID = 1L;

    private String financialCenterName;     //理财中心
    private Map<String,BusiType> types;     //业务区分
    private String sender;                  //经办人
    private List<Long> staffId;                   //经办人ID


    public static class BusiType{

        private String feLendNo;                    //出借编号
        private int count;                          //笔数
        private BigDecimal totalAmount;             //出借金额
        private String title;                       //业务类型名

        public BusiType() {
        }

        public BusiType(String feLendNo, int count, BigDecimal totalAmount, String title) {
            this.feLendNo = feLendNo;
            this.count = count;
            this.totalAmount = totalAmount;
            this.title = title;
        }

        public String getFeLendNo() {
            return feLendNo;
        }

        public void setFeLendNo(String feLendNo) {
            this.feLendNo = feLendNo;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


    }

    public static class OperatorGroup{
        String operatorName;
        String feLendNo;

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }

        public String getFeLendNo() {
            return feLendNo;
        }

        public void setFeLendNo(String feLendNo) {
            this.feLendNo = feLendNo;
        }

        public OperatorGroup(String operatorName, String feLendNo) {
            this.operatorName = operatorName;
            this.feLendNo = feLendNo;
        }

        public OperatorGroup() {
        }

        @Override
        public boolean equals(Object o) {
            Logger.debug(this.getClass(),"OperatorGroup class equals method is overriding...");
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OperatorGroup that = (OperatorGroup) o;

            /*if (operatorName != null ? !operatorName.equals(that.operatorName) : that.operatorName != null)
                return false;
            return feLendNo != null ? feLendNo.equals(that.feLendNo) : that.feLendNo == null;*/

            return (operatorName != null ? !operatorName.equals(that.operatorName) : that.operatorName != null);
        }

    }

    public ApplyMatchVo(String financialCenterName, Map<String, BusiType> types, String sender) {
        this.financialCenterName = financialCenterName;
        this.types = types;
        this.sender = sender;

    }

    public ApplyMatchVo() {
    }

    public String getFinancialCenterName() {
        return financialCenterName;
    }

    public void setFinancialCenterName(String financialCenterName) {
        this.financialCenterName = financialCenterName;
    }

    public Map<String, BusiType> getTypes() {
        return types;
    }

    public void setTypes(Map<String, BusiType> types) {
        this.types = types;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

	public List<Long> getStaffId() {
		return staffId;
	}

	public void setStaffId(List<Long> staffId) {
		this.staffId = staffId;
	}


}
