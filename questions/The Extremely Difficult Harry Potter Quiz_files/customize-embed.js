function $D(v){return(document.getElementById(v));}
function display_box()
{if($D("divBox").style.display=="none"||$D("divBox").style.display.length==0)
{$D("divBox").style.display="block";change_color_area='1';select_change_color_area();}
else
{$D("divBox").style.display="none";}}
var th_bgcolor='';var th_bcolor='';var th_fccolor='';function change_theme(b_color,bg_color,fc_color)
{if(bg_color.length!=0)
{$D("tblquiz").style.backgroundColor='#'+ bg_color;th_bgcolor=bg_color;}
if(b_color.length!=0)
{$D("tblquiz").style.borderColor='#'+ b_color;$D("hlk_heading").style.color='#'+ b_color;$D("tblquiz").style.color='#'+ b_color;th_bcolor=b_color;th_fccolor=b_color;}
if(fc_color.length!=0)
{$D("hlk_heading").style.color='#'+ fc_color;th_fccolor=fc_color;}
$D("embed_code").value=update_embed_code();}
function change_theme_def(b_color,bg_color,fc_color)
{if(bg_color.length!=0)
{$D("tblquiz").style.backgroundColor='#'+ bg_color;th_bgcolor=bg_color;}
if(b_color.length!=0)
{$D("tblquiz").style.borderColor='#'+ b_color;$D("hlk_heading").style.color='#'+ b_color;$D("tblquiz").style.color='#'+ b_color;th_bcolor=b_color;th_fccolor=b_color;}
if(fc_color.length!=0)
{$D("hlk_heading").style.color='#'+ fc_color;th_fccolor=fc_color;}}
function change_footer_firstime()
{$D("embed_code").value=$D("embed_code").value+ footer_text;}
var main_url='';var quiz_id='';var footer_text='';function update_embed_code()
{var embed_code_text="<iframe name='proprofs' id='proprofs' height='442' width='440' frameborder=0 marginwidth=0 marginheight=0 src='http://"+ main_url+"/quiz-school/widget/v2/?id="+ quiz_id+"&bgcolor="+ th_bgcolor+"&fcolor="+ th_bcolor+"&tcolor="+ th_fccolor+"'></iframe>"+ footer_text;return embed_code_text;}
function set_quiz_details(site_url,qid,foot_text)
{main_url=site_url;quiz_id=qid;footer_text=foot_text;}
function clearstyle(sel_theme)
{var frm=document.getElementsByTagName('a');for(var i=1;i<frm.length;i++)
{if(('link_theme_'+ i)==sel_theme)
{$D('link_theme_'+ i).className='theme_image_link selected';}
else
{if($D('link_theme_'+ i)!=null)
{$D('link_theme_'+ i).className='theme_image_link';}}}}
var change_color_area='';function select_change_color_area()
{$D("pick_color_0").className="";$D("pick_color_1").className="";$D("pick_color_2").className="";if(change_color_area.length!=0)
{$D("pick_color_"+ change_color_area).className="selected";}}