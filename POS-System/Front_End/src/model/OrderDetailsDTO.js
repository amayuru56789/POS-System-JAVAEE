function OrderDetailsDTO(oId,itemCode,price,qty,tot){
    var __OID=oId;
    var __oItemCode=itemCode;
    var __itemPrice=price;
    var __oQty=qty;
    var __oTotal=tot;

    this.getOID=function() {
        return __OID;
    }

    this.setOID=function(oid) {
        __OID=oid;
    }

    this.getItemCode=function() {
        return __oItemCode;
    }

    this.setItemCode=function(code) {
        __oItemCode=code;
    }

    this.getItemPrice=function() {
        return __itemPrice;
    }

    this.setItemPrice=function(price) {
        __itemPrice=price;
    }

    this.getOrderQTY=function() {
        return __oQty;
    }

    this.setOrderQTY=function() {
        __oQty=qty;
    }

    this.getOrderTotal=function() {
            return __oTotal;
        }

        this.setOrderTotal=function() {
            __oTotal=tot;
        }
}