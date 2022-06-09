function CustomerDTO(id,name,address,salary){
    var __id=id;
    var __name=name;
    var __address=address;
    var __salary=salary;

    this.getCustomerID=function() {
        return __id;
    }

    this.setCustomerID=function(id) {
        __id=id;
    }

    this.getCustomerName=function() {
        return __name;
    }

    this.setCustomerName=function(name) {
        __name=name;
    }

    this.getCustomerAddress=function() {
        return __address;
    }

    this.setCustomerAddress=function(address) {
        __address=address;
    }

    this.getCustomerSalary=function() {
        return __salary;
    }

    this.setCustomerSalary=function(salary) {
        __salary=salary;
    }
}