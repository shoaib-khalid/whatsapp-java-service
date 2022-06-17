package com.kalsym.whatsapp.service.model;

import io.swagger.models.auth.In;

import java.util.List;
import java.util.Set;

public class Response {
    public static class DailySalesReportResponse {
        public String date;
        public Integer totalTrx;
        public Float totalAmount;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getTotalTrx() {
            return totalTrx;
        }

        public void setTotalTrx(Integer totalTrx) {
            this.totalTrx = totalTrx;
        }

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }

    }

    public static class Sales {
        public String storeId;
        public String merchantName;
        public String storeName;
        public Float subTotal;
        public Float total;
        public Float serviceCharge;
        public Float deliveryCharge;
        public String customerName;
        public String orderStatus;
        public String deliveryStatus;
        public Float commission;
        public Float selfDeliveryCharge;
        public Float orderDiscount;
        public Float deliveryDiscount;

        public String getStoreId() { return storeId; }

        public void setStoreId(String storeId) { this.storeId = storeId; }

        public String getMerchantName() { return merchantName; };

        public void setMerchantName(String merchantName) { this.merchantName = merchantName; }

        public String getStoreName() { return storeName; }

        public void setStoreName(String storeName) { this.storeName = storeName; }

        public Float getTotal() { return  total; }

        public void setTotal(Float total) { this.total = total; }

        public Float getServiceCharge() { return serviceCharge; }

        public void setServiceCharge(Float serviceCharge) { this.serviceCharge = serviceCharge; }

        public Float getDeliveryCharge() { return deliveryCharge; }

        public void setDeliveryCharge(Float deliveryCharge) { this.deliveryCharge = deliveryCharge; }

        public String getCustomerName() { return customerName; }

        public void setCustomerName(String customerName) { this.customerName = customerName; }

        public String getOrderStatus() { return orderStatus; }

        public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

        public String getDeliveryStatus() { return deliveryStatus; }

        public void setDeliveryStatus(String deliveryStatus) { this.deliveryStatus = deliveryStatus; }

        public Float getCommission() { return  commission; }

        public void setCommission(Float commission) { this.commission = commission; }

        public Float getSubTotal() { return subTotal; }

        public void setSubTotal(Float subTotal) { this.subTotal = subTotal; }

        public Float getSelfDeliveryCharge() {
            return selfDeliveryCharge;
        }

        public void setSelfDeliveryCharge(Float selfDeliveryCharge) {
            this.selfDeliveryCharge = selfDeliveryCharge;
        }

        public Float getOrderDiscount() {
            return orderDiscount;
        }

        public void setOrderDiscount(Float orderDiscount) {
            this.orderDiscount = orderDiscount;
        }

        public Float getDeliveryDiscount() {
            return deliveryDiscount;
        }

        public void setDeliveryDiscount(Float deliveryDiscount) {
            this.deliveryDiscount = deliveryDiscount;
        }
    }

    public static class DetailedSalesReportResponse {
        public String date;
        private List<Sales> sales;

        public String getDate() { return date; }

        public void setDate(String date) { this.date = date; }

        public List<Sales> getSales() { return sales; }

        public void setSales(List<Sales> sales) { this.sales = sales; }
    }

    public static class SettlementResponse {
        public String merchantName;
        public Float gross;
        public Float fees;
        public Float nett;
        public Float Commission;
        public Float serviceFee;
        public Float refund;
        public String settlementDate;

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public Float getGross() {
            return gross;
        }

        public void setGross(Float gross) {
            this.gross = gross;
        }

        public Float getFees() {
            return fees;
        }

        public void setFees(Float fees) {
            this.fees = fees;
        }

        public Float getNett() {
            return nett;
        }

        public void setNett(Float nett) {
            this.nett = nett;
        }

        public Float getCommission() {
            return Commission;
        }

        public void setCommission(Float commission) {
            Commission = commission;
        }

        public Float getServiceFee() {
            return serviceFee;
        }

        public void setServiceFee(Float serviceFee) {
            this.serviceFee = serviceFee;
        }

        public Float getRefund() { return refund; }

        public void setRefund(Float refund) { this.refund = refund; }

        public String getSettlementDate() { return settlementDate; }

        public void setSettlementDate(String settlementDate) { this.settlementDate = settlementDate; }
    }

    public static class DetailedWeekSalesReportResponse {
        public String date;
        private List<Sales> sales;

        public String getDate() { return date; }

        public void setDate(String date) { this.date = date; }

        public List<Sales> getSales() { return sales; }

        public void setSales(List<Sales> sales) { this.sales = sales; }
    }

    public static class DetailedMonthSalesReportResponse {
        public String date;
        private List<Sales> sales;

        public String getDate() { return date; }

        public void setDate(String date) { this.date = date; }

        public List<Sales> getSales() { return sales; }

        public void setSales(List<Sales> sales) { this.sales = sales; }
    }

    public static class WeeklySalesReportResponse {
        public Integer weekNo;
        public String weekLabel;
        public Integer totalTrx;
        public Float totalAmount;

        public Integer getWeekNo() {
            return weekNo;
        }

        public void setWeekNo(Integer weekNo) {
            this.weekNo = weekNo;
        }

        public String getWeekLabel() {
            return weekLabel;
        }

        public void setWeekLabel(String weekLabel) {
            this.weekLabel = weekLabel;
        }

        public Integer getTotalTrx() {
            return totalTrx;
        }

        public void setTotalTrx(Integer totalTrx) {
            this.totalTrx = totalTrx;
        }

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }
    }

    public static class MonthlySalesReportResponse {
        public String monthNo;
        public String monthLabel;
        public Integer totalTrx;
        public Float totalAmount;

        public String getMonthNo() {
            return monthNo;
        }

        public void setMonthNo(String monthNo) {
            this.monthNo = monthNo;
        }

        public String getMonthLabel() {
            return monthLabel;
        }

        public void setMonthLabel(String monthLabel) {
            this.monthLabel = monthLabel;
        }

        public Integer getTotalTrx() {
            return totalTrx;
        }

        public void setTotalTrx(Integer totalTrx) {
            this.totalTrx = totalTrx;
        }

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }
    }


    public static class ProductInventoryResponse {
        public String productName;
        public Integer totalStock;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getTotalStock() {
            return totalStock;
        }

        public void setTotalStock(Integer totalStock) {
            this.totalStock = totalStock;
        }
    }

    public static class DailyTopProduct{
        private String productName;
        private Integer totalTransaction;
        private Integer rank;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getTotalTransaction() {
            return totalTransaction;
        }

        public void setTotalTransaction(Integer totalTransaction) {
            this.totalTransaction = totalTransaction;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }
    }

    public static class DailyTopProductResponse{
        private String date;
        private List<DailyTopProduct> topProduct;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<DailyTopProduct> getTopProduct() {
            return topProduct;
        }

        public void setTopProduct(List<DailyTopProduct> topProduct) {
            this.topProduct = topProduct;
        }
    }


    public static class WeeklyTopProduct{
        private String productName;
        private Integer totalTransaction;
        private Integer rank;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getTotalTransaction() {
            return totalTransaction;
        }

        public void setTotalTransaction(Integer totalTransaction) {
            this.totalTransaction = totalTransaction;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }
    }

    public static class WeeklyTopProductResponse{
        private Integer weekNo;
        private String week;
        private List<WeeklyTopProduct> topProduct;

        public Integer getWeekNo() {
            return weekNo;
        }

        public void setWeekNo(Integer weekNo) {
            this.weekNo = weekNo;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public List<WeeklyTopProduct> getTopProduct() {
            return topProduct;
        }

        public void setTopProduct(List<WeeklyTopProduct> topProduct) {
            this.topProduct = topProduct;
        }
    }



    public static class MonthlyTopProduct{
        private String productName;
        private Integer totalTransaction;
        private Integer rank;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getTotalTransaction() {
            return totalTransaction;
        }

        public void setTotalTransaction(Integer totalTransaction) {
            this.totalTransaction = totalTransaction;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        @Override
        public String toString() {
            return "{" +
                    "productName:'" + productName + '\'' +
                    ", totalTransaction:" + totalTransaction +
                    ", rank:" + rank +
                    '}';
        }
    }

    public static class MonthlyTopProductResponse{
        private String monthNo;
        private String monthLabel;
        private List<MonthlyTopProduct> topProduct;

        public String getMonthNo() {
            return monthNo;
        }

        public void setMonthNo(String monthNo) {
            this.monthNo = monthNo;
        }

        public String getMonthLabel() {
            return monthLabel;
        }

        public void setMonthLabel(String monthLabel) {
            this.monthLabel = monthLabel;
        }

        public List<MonthlyTopProduct> getTopProduct() {
            return topProduct;
        }

        public void setTopProduct(List<MonthlyTopProduct> topProduct) {
            this.topProduct = topProduct;
        }

        @Override
        public String toString() {
            return "{" +
                    "monthNo:'" + monthNo + '\'' +
                    ", monthLabel:'" + monthLabel + '\'' +
                    ", topProduct:" + topProduct +
                    '}';
        }
    }

    public static class MonthlyStatement{

        private String month;
        private String monthLabel;
        private String type;
        private int totalTrx;
        private float totalAmount;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getMonthLabel() {
            return monthLabel;
        }

        public void setMonthLabel(String monthLabel) {
            this.monthLabel = monthLabel;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getTotalTrx() {
            return totalTrx;
        }

        public void setTotalTrx(int totalTrx) {
            this.totalTrx = totalTrx;
        }

        public float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(float totalAmount) {
            this.totalAmount = totalAmount;
        }
    }

    public static class WeeklyStatement{

        private int weekNo;
        private String weekLabel;
        private String type;
        private int totalTrx;
        private float totalAmount;

        public int getWeekNo() {
            return weekNo;
        }

        public void setWeekNo(int weekNo) {
            this.weekNo = weekNo;
        }

        public String getWeekLabel() {
            return weekLabel;
        }

        public void setWeekLabel(String weekLabel) {
            this.weekLabel = weekLabel;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getTotalTrx() {
            return totalTrx;
        }

        public void setTotalTrx(int totalTrx) {
            this.totalTrx = totalTrx;
        }

        public float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(float totalAmount) {
            this.totalAmount = totalAmount;
        }
    }
}
