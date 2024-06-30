// 儲存已繪製的圖表實例
let hourlySalesChart = null;
let topSellingChart = null;

function drawCharts(response) {
    // ------- 每小時銷售數量數據 ---------
    let tmpTotalSales = 0;
    let hourlySalesData = [
        ['Hour', '銷售數量', '銷售額', '累積銷售額'],
    ];
    response.hourlySales.forEach(item => {
        tmpTotalSales += item.total;
        hourlySalesData.push([item.hour + ":00", item.count, item.total, tmpTotalSales]);
    });

    // ------- 最熱銷的前5名餐點數據 -------
    let topSellingData = [
        ['Dish', '數量'],
    ];
    response.dishRanking.forEach(dish => {
        topSellingData.push([dish.name.trim(), dish.count]);
    });

    // 繪製每小時銷售數量折線圖
    hourlySalesChart = new google.visualization.ComboChart(document.getElementById('hourlySalesChart'));
    hourlySalesChart.draw(google.visualization.arrayToDataTable(hourlySalesData), {
        title: '每小時銷售數量',
        // 銷售數量使用摺疊圖
        series: {
            0: { type: 'bars', targetAxisIndex: 1, color: 'orange' }, // 銷售數量使用第一個 Y 軸
            1: { type: 'line', targetAxisIndex: 0, color: '#934aa8' },  // 銷售額使用第二個 Y 軸
            2: { type: 'area', targetAxisIndex: 0, color: '#9fa39b' },
        },
        vAxes: {
            0: { title: '銷售額', format: 'currency' },
            1: { title: '銷售數量' },
        },
        hAxis: { title: 'Hour' }
    });

    // 繪製最熱銷餐點水平條形圖
    topSellingChart = new google.visualization.BarChart(document.getElementById('topSellingChart'));
    topSellingChart.draw(google.visualization.arrayToDataTable(topSellingData), {
        title: '最熱銷的前5名餐點',
        legend: { position: "bottom" },
    });
}

$(document).ready(function () {
    // 載入 Google Charts API
    google.charts.load('current', {
        packages: ['corechart']
    });
    getAnalyticsData($('#datePicker').val());

    $('#queryBtn').click(function () {
        let selectedDate = $('#datePicker').val();
        if (selectedDate === null || selectedDate === "") {
            alert("請輸入要查詢的日期");
            return;
        }
        getAnalyticsData(selectedDate);
    });
});

function updateTotalSales(totalSales) {
    // 顯示總銷售額
    $('#totalSales').html('<h5>當天總銷售額： $' + totalSales.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 }) + '</h5>');
}

// 獲取營業分析數據
function getAnalyticsData(selectedDate) {
    fetch(`/api/analytics?date=${selectedDate}`, {
        method: "GET",
        headers: {
            "accept": "application/json",
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(response => {
            console.log(response);
            // 當天的總銷售額數據
            let totalSales = response.totalSales;
            if (totalSales === null || totalSales === 0) {
                updateTotalSales(0);
                clearCharts(); // 清除圖表
            } else {
                updateTotalSales(totalSales);
                // 在這裡處理後端返回的數據，例如更新圖表或顯示相關資訊
                google.charts.setOnLoadCallback(function () {
                    drawCharts(response);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// 清除圖表
function clearCharts() {
    if (hourlySalesChart !== null) {
        hourlySalesChart.clearChart();
    }
    if (topSellingChart !== null) {
        topSellingChart.clearChart();
    }
}
