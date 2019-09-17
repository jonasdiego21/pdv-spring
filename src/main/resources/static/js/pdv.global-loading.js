/*
 * @author: Jonas Diego Rodrigues Martins
 * @Date: 29/07/2013 13:46
 * @Title: Global loading
 * @Description: This component was created to perform the global loading of all system elements. 
 */

(function($) {
	$.fn.preloader = function({timeDelay, timeEffect}) {
		$(this).delay(timeDelay).fadeOut(timeEffect);
		
		return this;
	}
}(jQuery))