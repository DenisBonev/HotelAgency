var ctx = document.getElementById("myChart");
let week = {
    monday: 0,
    tuesday: 0,
    wednesday: 0,
    thursday: 0,
    friday: 0,
    saturday: 0,
    sunday: 0
}
fetch("http://localhost:8080/stats/registered").then(res => res.json()).then(data => {
    week = data;
}).then(p => {
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
            datasets: [{
                data: [week.sunday, week.monday, week.tuesday, week.wednesday, week.thursday, week.friday, week.saturday],
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4,
                pointBackgroundColor: '#007bff'
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    stacked:true,
                    ticks: {
                        stepSize:1,
                        beginAtZero: true
                    }
                }]
            },
            legend: {
                display: false,
            }
        }
    })
});