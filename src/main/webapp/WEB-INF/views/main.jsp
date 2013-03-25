<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>storage service for cup data</title>
    <link href="../../resources/style.css" rel="stylesheet"/>
    <link href="../../resources/script/jquery.bxslider/jquery.bxslider.css" rel="stylesheet"/>
    <script src="../../resources/jquery-1.9.1.min.js"></script>
    <script src="../../resources/script/jquery.bxslider/jquery.bxslider.min.js"></script>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        var slider;
        $.ajax({
            url: "http://localhost:8080/cupdata/storage/",
            cache: false,
            dataType: "json",
            success: function (data) {
                var j = 0;
                $.each(data, function (i, item) {
                    if (item[0] == 'T') {
                        $("#bx-pager").append('<a data-slide-index="' + j + '" href=""><img src="http://localhost:8080/cupdata/storage/' + item + '"/></a>');
                        j++;
                    } else {
                        $(".bxslider").append('<li><img src="http://localhost:8080/cupdata/storage/' + item + '"/></li>');
                    }
                });
                slider = $('.bxslider').bxSlider({
                    pagerCustom: '#bx-pager'
                });
            }
        });
    });
</script>
<div class="example-item">
    <div id="bx-pager"></div>
</div>
<ul class="bxslider">
</ul>
<form id="uploadForm" method="POST" action="http://localhost:8080/cupdata/storage/store" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" value="上传"/>
</form>
</body>
</html>