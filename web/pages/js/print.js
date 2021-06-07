

function printDiv() {
            var divContents = document.getElementById("mainP").innerHTML;
            var a = window.open();
             
             
             a.document.write('<link rel="stylesheet" href="css/data-table/bootstrap-table.css"/>');
             a.document.write('<link rel="stylesheet" href="css/data-table/bootstrap-editable.css"/>');
             a.document.write('<link rel="stylesheet" href="css/bootstrap.min.css"/>');
             a.document.write('<link rel="stylesheet" href="css/font-awesome.min.css"/>');
             a.document.write('<link rel="stylesheet" href="css/owl.carousel.css"/>');
             a.document.write('<link rel="stylesheet" href="css/owl.theme.css"/>');
             a.document.write('<link rel="stylesheet" href="css/owl.transitions.css"/>');
             a.document.write('<link rel="stylesheet" href="css/animate.css"/>');
             a.document.write('<link rel="stylesheet" href="css/normalize.css"/>');
             a.document.write('<link rel="stylesheet" href="css/meanmenu.min.css"/>');
             a.document.write('<link rel="stylesheet" href="css/morrisjs/morris.css"/>');
             a.document.write('<link rel="stylesheet" href="css/scrollbar/jquery.mCustomScrollbar.min.css"/>');
             a.document.write('<link rel="stylesheet" href="css/metisMenu/metisMenu.min.css"/>');
             a.document.write('<link rel="stylesheet" href="css/metisMenu/metisMenu-vertical.css"/>');
            
            
             
             a.document.write('<link rel="stylesheet" href="css/editor/select2.css"/>');
             a.document.write('<link rel="stylesheet" href="css/editor/datetimepicker.css"/>');
             a.document.write('<link rel="stylesheet" href="css/editor/bootstrap-editable.css"/>');
             a.document.write('<link rel="stylesheet" href="css/editor/x-editor-style.css"/>');
             a.document.write('<link rel="stylesheet" href="css/data-table/bootstrap-table.css"/>');
             a.document.write('<link rel="stylesheet" href="css/data-table/bootstrap-editable.css"/>');
            
             a.document.write('<link rel="stylesheet" href="css/style.css"/>');
             a.document.write('<link rel="stylesheet" href="css/responsive.css"/>')
             a.document.write('<link rel="stylesheet" href="css/main.css"/>');
             
             
             a.document.write(divContents);
             
                setTimeout(function () {
            	 a.print();
                 a.document.close(); 
                }, 3000);

        }