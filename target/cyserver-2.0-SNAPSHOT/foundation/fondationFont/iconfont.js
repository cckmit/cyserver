;(function(window) {

  var svgSprite = '<svg>' +
    '' +
    '<symbol id="icon-hongxin" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M515.541 977.903c-3.074 0-6.15-0.75-8.95-2.287-0.933-0.532-1.85-1.136-2.673-1.794-1.483-1.098-5.984-4.211-12.648-8.822-29.102-20.244-97.266-67.615-142.181-102.355-167.57-130.466-256.543-227.074-272.083-295.401-7.963-24.216-12.374-51.672-12.777-79.952-0.969-75.154 27.327-147.581 77.626-198.703 45.356-46.143 104.825-71.695 167.368-71.952h0.038c79.363 0 150.639 35.876 197.697 97.266 45.942-63.33 117.07-100.961 195.226-102.025l4.742-0.036c134.293 0 245.102 118.569 246.95 264.284 0.33 28.171-3.404 55.735-11.147 81.945-13.397 67.101-102.225 168.979-263.572 301.13-63.641 52.514-115.368 91.483-153.658 115.807-3.038 1.941-6.48 2.893-9.959 2.893z"  ></path>' +
    '' +
    '<path d="M809.075 548.045c-1.262 0-2.524-0.129-3.825-0.403-9.994-2.088-16.435-11.916-14.35-21.927l2.783-11.276c3.441-11.86 5.087-24.015 4.905-36.314-0.769-62.213-42.209-110.902-94.41-110.902-10.25 0-18.578-8.292-18.578-18.542 0-10.248 8.254-18.522 18.504-18.522 72.756 0 130.54 64.775 131.547 147.471 0.238 15.924-1.886 31.793-6.333 47.076l-2.141 8.587c-1.811 8.747-9.516 14.753-18.102 14.753z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '</svg>'
  var script = function() {
    var scripts = document.getElementsByTagName('script')
    return scripts[scripts.length - 1]
  }()
  var shouldInjectCss = script.getAttribute("data-injectcss")

  /**
   * document ready
   */
  var ready = function(fn) {
    if (document.addEventListener) {
      if (~["complete", "loaded", "interactive"].indexOf(document.readyState)) {
        setTimeout(fn, 0)
      } else {
        var loadFn = function() {
          document.removeEventListener("DOMContentLoaded", loadFn, false)
          fn()
        }
        document.addEventListener("DOMContentLoaded", loadFn, false)
      }
    } else if (document.attachEvent) {
      IEContentLoaded(window, fn)
    }

    function IEContentLoaded(w, fn) {
      var d = w.document,
        done = false,
        // only fire once
        init = function() {
          if (!done) {
            done = true
            fn()
          }
        }
        // polling for no errors
      var polling = function() {
        try {
          // throws errors until after ondocumentready
          d.documentElement.doScroll('left')
        } catch (e) {
          setTimeout(polling, 50)
          return
        }
        // no errors, fire

        init()
      };

      polling()
        // trying to always fire before onload
      d.onreadystatechange = function() {
        if (d.readyState == 'complete') {
          d.onreadystatechange = null
          init()
        }
      }
    }
  }

  /**
   * Insert el before target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var before = function(el, target) {
    target.parentNode.insertBefore(el, target)
  }

  /**
   * Prepend el to target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var prepend = function(el, target) {
    if (target.firstChild) {
      before(el, target.firstChild)
    } else {
      target.appendChild(el)
    }
  }

  function appendSvg() {
    var div, svg

    div = document.createElement('div')
    div.innerHTML = svgSprite
    svgSprite = null
    svg = div.getElementsByTagName('svg')[0]
    if (svg) {
      svg.setAttribute('aria-hidden', 'true')
      svg.style.position = 'absolute'
      svg.style.width = 0
      svg.style.height = 0
      svg.style.overflow = 'hidden'
      prepend(svg, document.body)
    }
  }

  if (shouldInjectCss && !window.__iconfont__svg__cssinject__) {
    window.__iconfont__svg__cssinject__ = true
    try {
      document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>");
    } catch (e) {
      console && console.log(e)
    }
  }

  ready(appendSvg)


})(window)