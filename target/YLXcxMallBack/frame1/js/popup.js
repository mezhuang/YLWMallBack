function popup(timer) {
	var t ;
	h = $(document).scrollTop() +$(window).height() ;
	function popout() {
		clearTimeout(t);
		$('.alretbox').animate({
			top:'100%'
		},0,function() {
			$('.alretbox').hide();
		});
	}
	$('.alretbox .alretbox-title .ico-close').click(function() {
		popout();
	}).hover(function() {
		$(this).css({
			//color:'#000'
		})
	},function() {
		$(this).css({
			//color:'#777'
		})
	});
	$('.alretbox').show().animate({
		bootom:'100%'},0,function() {
		t = setTimeout(function() {
			popout();
		},timer? parseInt(timer):5500)
	});
	
}