// $('.dropdown-toggle').on('click', function () {
//     $(this).next('.dropdown-content').css('display', 'flex');
// });


$('.dropdown-toggle').on('click', function () {
    const $content = $(this).next('.dropdown-content');
    if ($content.css('display') === 'none') {
        $content.css('display', 'flex'); // flex
    } else {
        $content.css('display', 'none'); // 닫을 때는 none
    }
});

$('.job-group').on('click mouseover', function () {
    const selectedGroup = $(this).text();
})
