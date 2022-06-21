

//Save Customer

//btn select
document.getElementById("btnAdd").addEventListener("click", function (){

    //remove all the row click events
    $("#customerTable > tr").off("click");

    saveCustomer();
    clearAll();
    loadAllCustomers();

    $("#customerTable > tr").click(function() {
        /*console.log("row clicked");
        console.log(this);*/
        //console.log($(this));
        var customerID = $(this).children(":eq(0)").text();
        var customerName = $(this).children(":eq(1)").text();
        var Address = $(this).children(":eq(2)").text();
        var salary = $(this).children(":eq(3)").text();

        //console.log(customerID, customerName, Address, salary);

        /*set customer details for the textFields*/
        $("#txtCustId").val(customerID);
        $("#txtCustName").val(customerName);
        $("#txtCustAddress").val(Address);
        $("#txtCustSalary").val(salary);
    });

     /*remove row from the customerTable*/
     $("#customerTable > tr").dblclick(function(){
        $(this).remove();
    });
});

/*--------------------------------saveCustomer function for javaEE app----------------------------------------*/
function saveCustomer(){
    /*//gather customer information
       var custId = document.getElementById("txtCustId").value;
       var custName = document.getElementById("txtCustName").value;
       var custAddress = document.getElementById("txtCustAddress").value;
       var custSalary = document.getElementById("txtCustSalary").value;

       //create object

        /!*Pass Values for the customerArray*!/
       customerDB.push(new CustomerDTO(custId,custName,custAddress,custSalary));*/
    var data = $("#customerForm").serialize(); //get information txtFields using form data
    /*console.log(data);*/
    /*----------------------ajax for saveCustomer function---------------------------*/
    $.ajax({
       url: "http://localhost:8080/Pos_System/customer",
       method: "POST",
       data:data, //if we send data with the request
       success:function (res){
           if (res.status==200){
               alert(res.message);
               loadAllCustomers();
           }else {
               alert(res.data);
           }
           /*console.log(res.message);*/
       },
        error:function (ob,textStatus, error){
           alert(textStatus);
        }
    });
}

loadAllCustomers();
/*--------------------------------loadAllCustomers function for javaEE app------------------------------------*/
function loadAllCustomers() {
   /* $("#customerTable").empty();
    for(var i of customerDB){
        let row = `<tr><td>${i.getCustomerID()}</td><td>${i.getCustomerName()}</td><td>${i.getCustomerAddress()}</td><td>${i.getCustomerSalary()}</td></tr>`;
        $("#customerTable").append(row);
    }*/

    /*invoked ajax for send a request*/
    $("#customerTable").empty();
    $.ajax({
       url:"http://localhost:8080/Pos_System/customer",
       method:"GET",
        //convert to json format
       dataType:"json",
       success:function (res){
           /*console.log("KITT");*/
           for (const cust of res.data) {
               /*console.log(cust.id);*/
               let row = `<tr><td>${cust.id}</td><td>${cust.name}</td><td>${cust.address}</td><td>${cust.salary}</td></tr>`;
               $("#customerTable").append(row);
           }
       }
    });

//search customer
    $("#button-addon2").click(function () {
        var searchId = $("#txtsearchCustId").val();

        /*Pass search customerID for searchCustomer function*/
        var response = searchCustomer(searchId);

        if (response) {
            $("#txtCustId").val(response.getCustomerID);
            $("#txtCustName").val(response.getCustomerName);
            $("#txtCustAddress").val(response.getCustomerAddress);
            $("#txtCustSalary").val(response.getCustomerSalary);
        } else {
            clearAllCustomer();
            alert("No such a Customer");
        }
    });

    /*-------------------------------searchCustomer function for javaEE app-----------------------------------*/
    function searchCustomer(id) {
        for (let i = 0; i < customerDB.length; i++) {
            if (customerDB[i].getCustomerID() == id) {
                return customerDB[i];
            }
        }
    }

    /*-------------------------------updateCustomer function for javaEE app-----------------------------------*/
    function updateCustomer() {
       /* var custId = document.getElementById("txtCustId").value;
        var custName = document.getElementById("txtCustName").value;
        var custAddress = document.getElementById("txtCustAddress").value;
        var custSalary = document.getElementById("txtCustSalary").value;

        for (var i = 0; i < customerDB.length; i++) {
            if (customerDB[i].getCustomerID() == custId) {
                var customer = customerDB[i];
                customer.setCustomerName(custName);
                customer.setCustomerAddress(custAddress);
                customer.setCustomerSalary(custSalary);
            }
        }*/

        /*create json ob*/
        var customerOb={
            id:$("#txtCustId").val(),
            name:$("#txtCustName").val(),
            address:$("#txtCustAddress").val(),
            salary:$("#txtCustSalary").val()
        }

       /*let serialize = $("#customerForm").serialize();*/
       /*implement ajax request for updateCustomer Function*/
        $.ajax({
          url:"http://localhost:8080/Pos_System/customer",
          method:"PUT",
          contentType:"application/json", //request content type json
          data:JSON.stringify(customerOb) , //convert valid json String
          success:function (res){
              /*console.log(res);*/
              if (res.status==200){
                  alert(res.message);
                  loadAllCustomers();
              }else if (res.status==400){
                  alert(res.message);

              }else {
                  alert(res.data);
              }
              /*alert(res);
              loadAllCustomers();*/
          },
          error:function (ob, error) {
              console.log(ob);
          }
       });
    }

    $("#btnCustUpdate").click(function () {
        updateCustomer();
        loadAllCustomers();
    });

    /*------------------------------deleteCustomer function for javaEE app------------------------------------*/
    function deleteCustomer(id) {
        /*let customer;
        if (id != null) {
            for (var i = 0; i < customerDB.length; i++) {
                if (id == customerDB[i].getCustomerID()) {
                    customer = customerDB[i];
                }
            }
            let index = customerDB.indexOf(customer);
            customerDB.splice(index, 1);
            return true;
        } else {
            return false;
        }*/

        /*ajax request for deleteCustomer function*/
        /*var data = $("#customerForm").serialize();*/
        let customerID = $("#txtCustId").val(); //Get the customer ID
        $.ajax({
           url:"http://localhost:8080/Pos_System/customer?CustID="+id, //Send a request using query String
           method:"DELETE",
           /*data:data,*/
           success:function (res){
               /*console.log(res);*/
               if (res.status==200){
                   alert(res.message);
                   loadAllCustomers();
               }else if (res.status==400){
                   alert(res.data);
               }else {
                   alert(res.data);
               }
               /*alert(res);
               loadAllCustomers();*/
           },
           error:function (ob,status,a){
               console.log(ob);
               console.log(status);
               console.log(a);
           }
        });

    }

    $("#btnCustDelete").click(function () {
        //get customerID
        let id = $("#txtCustId").val();

        let option = confirm(`Do you want delete Customer : ${id} `);
        if (option) {
            if (deleteCustomer(id)) {
                alert("Customer delete Success");
                loadAllCustomers();
            } else {
                alert("Try again...");
            }
        }
    });

//validation
// customer reguler expressions
    const regExCustId = /^(C00-)[0-9]{1,3}$/;
    const regExCustName = /^[A-z ]{5,20}$/;
    const regExCustAddress = /^[0-9/A-z. ,]{7,}$/;
    const regExCustSalary = /^[0-9]{1,}[.]?[0-9]{1,2}$/;

    $('#txtCustId,#txtCustName,#txtCustAddress,#txtCustSalary').on('keydown', function (eventOb) {
        if (eventOb.key == "Tab") {
            eventOb.preventDefault();
        }
    });

    $('#txtCustId,#txtCustName,#txtCustAddress,#txtCustSalary').on('blur', function () {
        formValidCustomer();
    });

//focusing events
    $("#txtCustId").on('keyup', function (eventOb) {
        setButtonCust();
        if (eventOb.key == "Enter") {
            checkIfValidCustomer();
        }

        if (eventOb.key == "Control") {
            var typedCustomerID = $("#txtCustId").val();
            /*var srcCustomer = searchCustomerFromID(typedCustomerID);*/
            $("#txtCustId").val(srcCustomer.getCustomerID());
            $("#txtCustName").val(srcCustomer.getCustomerName());
            $("#txtCustAddress").val(srcCustomer.getCustomerAddress());
            $("#txtCustSalary").val(srcCustomer.getCustomerSalary());
        }

    });

    $("#txtCustName").on('keyup', function (eventOb) {
        setButtonCust();
        if (eventOb.key == "Enter") {
            checkIfValidCustomer();
        }
    });

    $("#txtCustAddress").on('keyup', function (eventOb) {
        setButtonCust();
        if (eventOb.key == "Enter") {
            checkIfValidCustomer();
        }
    });

    $("#txtCustSalary").on('keyup', function (eventOb) {
        setButtonCust();
        if (eventOb.key == "Enter") {
            checkIfValidCustomer();
        }
    });
// focusing events end

    $("#btnAdd").attr('disabled', true);

    function clearAllCustomer() {
        $('#txtCustId,#txtCustName,#txtCustAddress,#txtCustSalary').val("");
        $('#txtCustId,#txtCustName,#txtCustAddress,#txtCustSalary').css('border', '2px solid #ced4da');
        $('#txtCustId').focus();
        $("#btnAdd").attr('disabled', true);
        loadAllCustomers();
        $("#lblcustid,#lblcustname,#lblcustaddress,#lblcustsalary").text("");
    }

    function formValidCustomer() {
        var custId = $("#txtCustId").val();
        $("#txtCustId").css('border', '2px solid green');
        $("#lblcustid").text("");
        if (regExCustId.test(custId)) {
            var custName = $("#txtCustName").val();
            if (regExCustName.test(custName)) {
                $("#txtCustName").css('border', '2px solid green');
                $("#lblcustname").text("");
                var custAddress = $("#txtCustAddress").val();
                if (regExCustAddress.test(custAddress)) {
                    var cusSalary = $("#txtCustSalary").val();
                    var resp = regExCustSalary.test(cusSalary);
                    $("#txtCustAddress").css('border', '2px solid green');
                    $("#lblcustaddress").text("");
                    if (resp) {
                        $("#txtCustSalary").css('border', '2px solid green');
                        $("#lblcustsalary").text("");
                        return true;
                    } else {
                        $("#txtCustSalary").css('border', '2px solid red');
                        $("#lblcustsalary").text("Cus Salary is a required field : Pattern 100.00 or 100");
                        return false;
                    }
                } else {
                    $("#txtCustAddress").css('border', '2px solid red');
                    $("#lblcustaddress").text("Cus Name is a required field : Mimum 7");
                    return false;
                }
            } else {
                $("#txtCustName").css('border', '2px solid red');
                $("#lblcustname").text("Cus Name is a required field : Mimimum 5, Max 20, Spaces Allowed");
                return false;
            }
        } else {
            $("#txtCustId").css('border', '2px solid red');
            $("#lblcustid").text("Cus ID is a required field : Pattern C00-000");
            return false;
        }
    }

    function checkIfValidCustomer() {
        var custId = $("#txtCustId").val();
        if (regExCustId.test(custId)) {
            $("#txtCustName").focus();
            var custName = $("#txtCustName").val();
            if (regExCustName.test(custName)) {
                $("#txtCustAddress").focus();
                var custAddress = $("#txtCustAddress").val();
                if (regExCustAddress.test(custAddress)) {
                    $("#txtCustSalary").focus();
                    var custSalary = $("#txtCustSalary").val();
                    var resp = regExCustSalary.test(custSalary);
                    if (resp) {
                        let res = confirm("Do you really need to add this Customer..?");
                        if (res) {
                            saveCustomer();
                            clearAllCustomer();
                        }
                    } else {
                        $("#txtCustSalary").focus();
                    }
                } else {
                    $("#txtCustAddress").focus();
                }
            } else {
                $("#txtCustName").focus();
            }
        } else {
            $("#txtCustId").focus();
        }
    }

    function setButtonCust() {
        let b = formValidCustomer();
        if (b) {
            $("#btnAdd").attr('disabled', false);
        } else {
            $("#btnAdd").attr('disabled', true);
        }
    }

    $('#btnAdd').click(function () {
        checkIfValidCustomer();
    });
}
//validation ended

/*save item*//*

document.getElementById("btnItem").addEventListener("click", function (){

    //remove all the row click events
    $("#itemTable > tr").off("click");

    //gather item information
    var itemCode = document.getElementById("txtCode").value;
    var itemName = document.getElementById("txtItemName").value;
    var price = document.getElementById("txtPrice").value;
    var qty = document.getElementById("txtQty").value;

    */
/*create a row*//*

    var row = `<tr><td>${itemCode}</td><td>${itemName}</td><td>${price}</td><td>${qty}</td></tr>`;
   */
/* console.log(row);*//*


    */
/*select the body and added the row *//*

    //document.getElementById("itemTable").append(row);
    $("#itemTable").append(row);

    $("#itemTable > tr").click(function() {
            console.log("row clicked");
            console.log(this);

            var itemCode = $(this).children(":eq(0)").text();
            var itemName = $(this).children(":eq(1)").text();
            var price = $(this).children(":eq(2)").text();
            var qty = $(this).children(":eq(3)").text();

            */
/*set item details for the textFields*//*

            $("#txtCode").val(itemCode);
            $("#txtItemName").val(itemName);
            $("#txtPrice").val(price);
            $("#txtQty").val(qty);
    });
});
*/
