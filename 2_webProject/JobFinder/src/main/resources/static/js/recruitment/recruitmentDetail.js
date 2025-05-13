$(document).ready(function () {
    const deadlineStr = $("#endDateRaw").data("deadline");

    if (!deadlineStr) return;

    const deadline = new Date(deadlineStr);

    function updateCountdown() {
        const now = new Date();
        const diff = deadline - now;

        if (diff <= 0) {
            $("#deadlineCountdown").text("마감되었습니다");
            return;
        }

        const days = Math.floor(diff / (1000 * 60 * 60 * 24));
        const hours = String(Math.floor((diff / (1000 * 60 * 60)) % 24)).padStart(2, '0');
        const minutes = String(Math.floor((diff / (1000 * 60)) % 60)).padStart(2, '0');
        const seconds = String(Math.floor((diff / 1000) % 60)).padStart(2, '0');

        $("#deadlineCountdown").html(`${days}일 ${hours}:${minutes}:${seconds}`);
    }

    updateCountdown();
    setInterval(updateCountdown, 1000);
});
