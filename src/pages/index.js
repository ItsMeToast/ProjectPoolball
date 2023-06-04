
$(function() {
    const openingSong = $("#song");
    const audioBtn = $("#audio-btn");

    //Popup
    const popupBtn = $("#popup-btn");
    popupBtn.on("click", function() {
        openingSong.attr("src", `src/public/openingSong${Math.floor(Math.random() * 3) + 1}.mp3`)
        openingSong.get(0).play();
        $(".popup").hide();
    });

    //Audio Player functionality
    audioBtn.on("click", function() {
        openingSong.prop("muted", !(openingSong.prop("muted")));
        openingSong.prop("muted") ? audioBtn.attr("src", "src/public/noaudio.png") : audioBtn.attr("src", "src/public/audio.png");
    });
});
