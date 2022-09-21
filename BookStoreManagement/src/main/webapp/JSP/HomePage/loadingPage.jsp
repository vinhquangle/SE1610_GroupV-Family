<%-- 
    Document   : testPage
    Created on : Jun 14, 2022, 5:08:31 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Loading Page</title>
    </head>
    <body>
        <form action="MainController" method="POST">
            <input name="action" value="Get" type="hidden"/> 
            <input id="count" value="1" type="hidden"/>
        </form> 
        <div class="wrapper">
            <div class="circle"></div>
            <div class="circle"></div>
            <div class="circle"></div>
            <div class="shadow"></div>
            <div class="shadow"></div>
            <div class="shadow"></div>
            <span>Loading</span>
        </div>
    </body>
    <style>
        body{
            padding:0;
            margin:0;
            width:100%;
            height:100vh;
            background:radial-gradient(#9b59b6, #8e44ad);
        }
        .wrapper{
            width:200px;
            height:60px;
            position: absolute;
            left:50%;
            top:50%;
            transform: translate(-50%, -50%);
        }
        .circle{
            width:20px;
            height:20px;
            position: absolute;
            border-radius: 50%;
            background-color: #fff;
            left:15%;
            transform-origin: 50%;
            animation: circle .5s alternate infinite ease;
        }

        @keyframes circle{
            0%{
                top:60px;
                height:5px;
                border-radius: 50px 50px 25px 25px;
                transform: scaleX(1.7);
            }
            40%{
                height:20px;
                border-radius: 50%;
                transform: scaleX(1);
            }
            100%{
                top:0%;
            }
        }
        .circle:nth-child(2){
            left:45%;
            animation-delay: .2s;
        }
        .circle:nth-child(3){
            left:auto;
            right:15%;
            animation-delay: .3s;
        }
        .shadow{
            width:20px;
            height:4px;
            border-radius: 50%;
            background-color: rgba(0,0,0,.5);
            position: absolute;
            top:62px;
            transform-origin: 50%;
            z-index: -1;
            left:15%;
            filter: blur(1px);
            animation: shadow .5s alternate infinite ease;
        }

        @keyframes shadow{
            0%{
                transform: scaleX(1.5);
            }
            40%{
                transform: scaleX(1);
                opacity: .7;
            }
            100%{
                transform: scaleX(.2);
                opacity: .4;
            }
        }
        .shadow:nth-child(4){
            left: 45%;
            animation-delay: .2s
        }
        .shadow:nth-child(5){
            left:auto;
            right:15%;
            animation-delay: .3s;
        }
        .wrapper span{
            position: absolute;
            top:75px;
            font-family: 'Lato';
            font-size: 20px;
            letter-spacing: 12px;
            color: #fff;
            left:15%;
        }







        /*  footer   */
        footer {
            background-color: #222;
            color: #fff;
            font-size: 14px;
            bottom: 0;
            position: fixed;
            left: 0;
            right: 0;
            text-align: center;
            z-index: 999;
        }

        footer p {
            margin: 10px 0;
            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida  Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
        }
        footer .fa-heart{
            color: red;
        }
        footer .fa-dev{
            color: #fff;
        }
        footer .fa-twitter-square{
            color:#1da0f1;
        }
        footer .fa-instagram{
            color: #f0007c;
        }
        fotter .fa-linkedin{
            color:#0073b1;
        }
        footer .fa-codepen{
            color:#fff
        }
        footer a {
            color: #3c97bf;
            text-decoration: none;
            margin-right:5px;
        }
        .youtubeBtn{
            position: fixed;
            left: 50%;
            transform:translatex(-50%);
            bottom: 45px;
            cursor: pointer;
            transition: all .3s;
            vertical-align: middle;
            text-align: center;
            display: inline-block;
        }
        .youtubeBtn i{
            font-size:20px;
            float:left;
        }
        .youtubeBtn a{
            color:#ff0000;
            animation: youtubeAnim 1000ms linear infinite;
            float:right;
        }
        .youtubeBtn a:hover{
            color:#c9110f;
            transition:all .3s ease-in-out;
            text-shadow: none;
        }
        .youtubeBtn i:active{
            transform:scale(.9);
            transition:all .3s ease-in-out;
        }
        .youtubeBtn span{
            font-family: 'Lato';
            font-weight: bold;
            color: #fff;
            display: block;
            font-size: 12px;
            float: right;
            line-height: 20px;
            padding-left: 5px;

        }

        @keyframes youtubeAnim{
            0%,100%{
                color:#c9110f;
            }
            50%{
                color:#ff0000;
            }
        }
        /* footer  */
    </style>
    <script type="text/javascript">
        
        window.onload = formSubmit;     
        function formSubmit() {
            setTimeout(() => {
    openPopup()
    }, 800)
        
        }
        function openPopup() {
  window.location.hash = 'openModal';
  document.forms[0].submit();

}
    </script>
</html>