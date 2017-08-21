function wsug(e, str){ 
 var oThis = arguments.callee;
 if(!str) {
  oThis.sug.style.visibility = 'hidden';
  document.onmousemove = null;
  return;
 }
 if(!oThis.sug){
  var div = document.createElement('div');
  div.className='title_div';
  var sug = document.createElement('div');
  sug.className='title_sug';
  var dr = document.createElement('div');
  dr.className='title_text';
   dr.style.cssText = css;
   dr.setAttribute('style',css);
  var ifr = document.createElement('iframe'), css='position:absolute; left:0; top:0; z-index:8; filter:alpha(opacity=0); opacity:0';
   ifr.style.cssText = css;
   ifr.setAttribute('style',css);
  div.appendChild(ifr);
  div.appendChild(dr);
  div.appendChild(sug);
  div.sug = sug;
  document.body.appendChild(div);
  oThis.sug = div;
  oThis.dr = dr;
  oThis.ifr = ifr;
  div = dr = ifr = sug = null;
 }
var e = e || window.event;
var obj = oThis.sug;
var dr = oThis.dr;
var ifr = oThis.ifr;
 obj.sug.innerHTML = str;

 var w = obj.sug.offsetWidth, h = obj.sug.offsetHeight, dw = document.documentElement.clientWidth||document.body.clientWidth; dh = document.documentElement.clientHeight || document.body.clientHeight;
 var st = document.documentElement.scrollTop || document.body.scrollTop, sl = document.documentElement.scrollLeft || document.body.scrollLeft;
 var left = e.clientX +sl +17 + w < dw + sl && e.clientX + sl + 15 || e.clientX +sl-8 - w, top = e.clientY + st +17 + h < dh + st && e.clientY + st + 17 || e.clientY + st - 5 - h;
 obj.style.left = left+ 10 + 'px';
 obj.style.top = top + 10 + 'px';
 dr.style.width = w + 'px';
 dr.style.height = h + 'px';
 ifr.style.width = w + 3 + 'px';
 ifr.style.height = h + 3 + 'px';
 obj.style.visibility = 'visible';
 document.onmousemove = function(e){
  var e = e || window.event, st = document.documentElement.scrollTop || document.body.scrollTop, sl = document.documentElement.scrollLeft || document.body.scrollLeft;
  var left = e.clientX +sl +17 + w < dw + sl && e.clientX + sl + 15 || e.clientX +sl-8 - w, top = e.clientY + st +17 + h < dh + st && e.clientY + st + 17 || e.clientY + st - 5 - h;
  obj.style.left = left + 'px';
  obj.style.top = top + 'px';
 }
}