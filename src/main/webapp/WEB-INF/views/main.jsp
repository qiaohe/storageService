<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>storage service for cup data</title>
    <link href="../../resources/style.css" rel="stylesheet"/>
    <link href="../../resources/script/jquery.bxslider/jquery.bxslider.css" rel="stylesheet"/>
    <script src="../../resources/jquery-1.9.1.min.js"></script>
    <script src="../../resources/script/jquery.bxslider/jquery.bxslider.min.js"></script>
    <script src="../../resources/script/transit.js"></script>
</head>
<body>
<script type="text/javascript">
    var currentSlide;
    var slider;
    $(document).ready(function () {
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
                    pagerCustom: '#bx-pager',
                    onSlideAfter: function(slide, oldIndex, newIndex) {
                        currentSlide = slide;
                    }
                });
            }
        });
    });

    function circle(deg){
        if (!currentSlide) return;
        var img = $(currentSlide[0].firstChild);
        var degree = img.data('rotateValue') || 0;
        degree += deg;
        if (deg == 0) {
            degree = 0;
        }
        img.data('rotateValue', degree);
        img.transition({rotate: degree + 'deg'});
    }
</script>
<div class="example-item">
    <div id="bx-pager"></div>
</div>
<div id="rotateButton">
    <div class="span4">
        <span class="span4">
            <a href="javascript:;" onclick="javascript:circle(-90);">
                <img width="20px;" alt="左转" src="../../resources/lroll.png" />
            </a>
        </span>
        <span class="span4">
            <a href="javascript:;" onclick="javascript:circle(0);">
                <img width="20px;" alt="重置" src="../../resources/reset.png" />
            </a>
        </span>
        <span class="span4">
            <a href="javascript:;" onclick="javascript:circle(90);">
                <img width="20px;" alt="右转" src="../../resources/rroll.png" />
            </a>
        </span>
    </div>
</div>
<ul class="bxslider">
</ul>
<form id="uploadForm" method="POST" action="http://localhost:8080/cupdata/storage/store" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" value="上传"/>
</form>
</body>
</html>