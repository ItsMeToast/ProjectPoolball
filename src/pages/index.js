
$(function() {
    //Audio Player functionality
    const openingSong = $("#song");
    const audioBtn = $("#audio-btn");

    $('#audio-btn').on("click", function() {
        openingSong.get(0).play();
        openingSong.prop("muted", !(openingSong.prop("muted")));
        openingSong.prop("muted") ? audioBtn.attr("src", "src/public/noaudio.png") : audioBtn.attr("src", "src/public/audio.png");
    });
});
