$(function(){
    
    $(".btn_register").click(function(e){
        e.preventDefault();
        $(".login_box_visibility").fadeOut(300,function(){
            
            $(".register_box_visibility").fadeIn(100);
            $(".login_box_bg").animate({
                height: 620
            });
        });
        
    });
    
     $(".back_login").click(function(e){
        e.preventDefault();
        $(".register_box_visibility").fadeOut(300,function(){
            
            $(".login_box_visibility").fadeIn(100);
            $(".login_box_bg").animate({
                height: 520
            });
        });
        
    });
    
    

});