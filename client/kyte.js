var pps_pos_list = [{}];


get_cursor_position = function() {

}


$('#page')
    .attr('unselectable', 'on')
    .css('-webkit-user-select', 'none')
    .css('-moz-user-select', 'none')
    .css("-ms-user-select","none")
    .css("-o-user-select","none")
    .css("user-select",'none')
    .on('selectstart', false)
    .on('mousedown', false);
