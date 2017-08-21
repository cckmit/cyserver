var contactListTpl = '<% for(var i = 0;i < lists.length; i++){ %>' +
    '<li style="margin-top: 10px;padding: 0px 10px;background-color: #ffffff">' +
    '   <ul style="padding: 10px 0px;line-height: 14px;border-bottom: 1px solid #d9d9d9;">' +
    '       <li style="font-size: 12px;color: #7B7B7B;"><%=lists[i].createTime%></li>' +
    '       <li style="font-size: 14px;padding-top: 10px;"><%=lists[i].content%></li>' +
    '   </ul>' +
    '   <ul style="padding: 10px 0px;">' +
    '       <li>'+
    '           <div style="float: left;"><img src="<%=lists[i].userAvatar%>" width="40" height="40"></div>'+
    '           <ul style="padding-left: 50px;">'+
    '               <li style="font-size: 14px;">校方回复：</li>'+
    '               <li style="font-size: 12px;color: #7B7B7B;"><%=(lists[i].replyTime ? lists[i].replyTime : "&nbsp;&nbsp;")%></li>'+
    '           </ul>'+
    '       </li>' +
    '       <li style="font-size: 14px;padding-top: 10px;"><%=lists[i].replyContent %></li>' +
    '   </ul>' +
    '</li>' +
    '<% } %>';

