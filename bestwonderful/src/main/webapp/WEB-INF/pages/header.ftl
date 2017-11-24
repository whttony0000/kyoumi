
    <style>
        .fh5co-nav {
            background: #fff;
            display: block;
        }

        .fh5co-nav .top-menu {
            padding: 28px 0;
            box-sizing: border-box;
            box-shadow: 0 0 8px 0 rgba(0,0,0,.16);
        }

        .container::before {
            content: " ";
            display: table;
        }

        *::before {
            box-sizing: border-box;
        }

        .row {
            margin-left: -15px;
            margin-right: -15px;
            box-sizing: border-box;
        }

        .row::before {
            content: " ";
            display: table;
        }

        .col-xs-2 {
            width: 16.66667%;
            float: left;
            position: relative;
            min-height: 1px;
            padding-left: 15px;
            padding-right: 15px;
        }

        * {
            box-sizing: border-box;
        }

        .fh5co-nav #fh5co-logo {
            font-size: 32px;
            margin: 0;
            padding: 0;
            /*text-transform: uppercase;*/
            font-weight: 700;
            font-family: "Inconsolata",Arial,sans-serif;
        }

        .fh5co-nav a {
            padding: 5px 10px;
            color: #000;
        }

        .col-xs-10 {
            width: 83.33333%;
            float: left;
            position: relative;
            min-height: 1px;
            padding-left: 15px;
            padding-right: 15px;
        }

        .text-right {
            text-align: right;
        }

        .row::after {
            content: " ";
            display: table;
            clear: both;
        }

        .container::after {
            clear: both;
            content: " ";
            display: table;
        }

        *::after {
            box-sizing: border-box;
        }

        .fh5co-nav #fh5co-logo a span {
            color: #20A0FF;
        }

        .fh5co-nav ul li {
            padding: 0;
            margin: 0;
            list-style: none;
            display: inline;
        }

        .fh5co-nav ul li.active > a {
            color: #000 !important;
            position: relative;
        }

        .fh5co-nav ul li a {
            font-size: 18px;
            padding: 30px 15px;
            -webkit-transition: 0.5s;
            -o-transition: 0.5s;
            transition: 0.5s;
        }

        .fh5co-nav ul li.h-l > a:hover::after {
            position: absolute;
            margin-bottom: 25px;
            padding:1px 20px 1px 15px;
            content: '';
            background: #20A0FF;
            margin-top: 35px;
            margin-left: -40px;
            display: inline-block;
        }

        .fh5co-nav ul li.l-category > a:hover::after {
            margin-left: -50px;
        }

        .fh5co-nav ul li.l-contact > a:hover::after {
            margin-left: -50px;
        }

        .fh5co-nav ul li.btn-cta a {
            padding: 30px 0px !important;
            color: #fff;
        }

        .fh5co-nav ul li.has-dropdown .dropdown {
            width: 140px;
            -webkit-box-shadow: 0px 14px 33px -9px rgba(0, 0, 0, 0.75);
            -moz-box-shadow: 0px 14px 33px -9px rgba(0, 0, 0, 0.75);
            box-shadow: 0px 14px 33px -9px rgba(0, 0, 0, 0.75);
            z-index: 1002;
            visibility: hidden;
            opacity: 0;
            position: absolute;
            top: 40px;
            left: 0;
            text-align: left;
            background: #000;
            padding: 20px;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            -ms-border-radius: 4px;
            border-radius: 4px;
            -webkit-transition: 0s;
            -o-transition: 0s;
            transition: 0s;
        }

        .fh5co-nav ul li.has-dropdown .dropdown::before {
            bottom: 100%;
            left: 40px;
            border: solid transparent;
            border-top-width: medium;
            border-right-width: medium;
            border-bottom-width: medium;
            border-left-width: medium;
            border-bottom-color: transparent;
            content: " ";
            height: 0;
            width: 0;
            position: absolute;
            pointer-events: none;
            border-bottom-color: #000;
            border-width: 8px;
            margin-left: -8px;
        }

        .fh5co-nav ul li.has-dropdown .dropdown li a {
            padding: 2px 0;
            display: block;
            color: #999999;
            line-height: 1.2;
            text-transform: none;
            font-size: 13px;
            letter-spacing: 0;
        }

        .fh5co-nav ul li.has-dropdown .dropdown li {
            display: block;
            margin-bottom: 7px;
        }

        .fh5co-nav ul li.btn-cta a span {
            background: #20A0FF;
            padding: 4px 10px;
            display: -moz-inline-stack;
            display: inline-block;
            zoom: 1;
            *display: inline;
            -webkit-transition: 0.3s;
            -o-transition: 0.3s;
            transition: 0.3s;
            -webkit-border-radius: 100px;
            -moz-border-radius: 100px;
            -ms-border-radius: 100px;
            border-radius: 100px;
        }

        a:hover {
            text-decoration: none;
        }
    </style>



    <nav class="fh5co-nav" role="navigation" id="headerContainer">
        <div class="top-menu">
            <div class="container">
                <div class="row">
                    <div class="col-xs-2">
                        <div id="fh5co-logo"><a href="/index">Be<span>st</span>W<span>on</span>d<span>e</span>rful</a></div>
                    </div>
                    <div class="col-xs-10 text-right menu-1">
                        <ul>
                            <li class="l-home h-l"><a href="/index">Home</a></li>
                            <li class="l-category h-l"><a href="/categoryList">Category</a></li>
                            <li class="l-article h-l"><a href="/articleList">Article</a></li>
                            <li class="l-article h-l"><a href="/data/index">Data</a></li>
                            <li class="l-about h-l"><a href="/about">About</a></li>
                            <li class="l-contact h-l"><a href="/contact">Contact</a></li>
                            <li class="btn-cta"><a href="/logout"><span>Login in/out</span></a></li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </nav>
<script>
    var headerVue = new Vue({
        el:'#headerContainer',
        data :function () {
            return {
                activeIndex: '1',
            }
        },
        methods: {
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    })
</script>