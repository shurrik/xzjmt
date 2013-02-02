<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>闲置姐妹淘</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="${cssroot}/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>

  </head>

  <body>

    <div class="container">

      <form action="${wwwroot}/reg/email" class="form-signin" method="post">
        <h2 class="form-signin-heading">闲置姐妹淘注册</h2>
        <input name="to" type="text" class="input-block-level" placeholder="输入你的注册邮箱" required>
        <button class="btn btn-large btn-primary" type="submit">发送</button>
      </form>

    </div> <!-- /container -->


  </body>
</html>

