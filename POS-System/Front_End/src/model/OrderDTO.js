function OrderDTO(oId,custName,total,date) {
    var __oID=oId;
    var __custName=custName;
    var __total=total;
    var __orderDate=date;

    this.getOrderID=function() {
        return __oID;
    }

    this.setOrderID=function(oID) {
        __oID=oID;
    }

    this.getCustName=function() {
        return __custName;
    }

     this.setCustName=function(custID) {
        __custName=custID;
     }

     this.getOrderTotal=function() {
        return __total;
     }

     this.setOrderTotal=function() {
        __total=total;
     }

     this.getOrderDate=function() {
        return __orderDate;
     }

     this.setOrderDate=function() {
        __orderDate=date;
     }
}