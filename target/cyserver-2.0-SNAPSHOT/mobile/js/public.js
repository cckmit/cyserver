/**
 * 公用JS
 */

//JS 得到当前项目的全路径
function getRootPath()
{   
	var pathName = window.location.pathname.substring(1);   
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));   
    return window.location.protocol + '//' + window.location.host + '/'+ webName + '/';   
}   


function getIcons(iconsId)
{
	//alert(iconsId);
	if(!isNaN(iconsId))
	{
		var icons = new Array();
		icons[0] = "picture_0.png";
		icons[1] = "picture_1.png";
		icons[2] = "picture_2.png";
		icons[3] = "picture_3.png";
		icons[4] = "picture_4.png";
		icons[5] = "picture_5.png";
		icons[6] = "picture_6.png";
		icons[7] = "picture_7.png";
		icons[8] = "picture_8.png";
		icons[9] = "picture_9.png";
		icons[10] = "picture_10.png";
		icons[11] = "picture_11.png";
		icons[12] = "picture_12.png";
		icons[13] = "picture_13.png";
		icons[14] = "picture_14.png";
		icons[15] = "picture_15.png";
		icons[16] = "picture_16.png";
		icons[17] = "picture_17.png";
		icons[18] = "picture_18.png";
		icons[19] = "picture_19.png";
		
		//alert(getRootPath() + "mobile/img/icons/" + icons[iconsId]);
		
		return getRootPath() + "mobile/img/icons/" + icons[iconsId];
	}
	else
	{
		return iconsId;
	}
	
}