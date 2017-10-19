var host = "http://planex.herokuapp.com/";
var ctx = document.getElementById("graficoElem").getContext('2d');
function today() {
  var date = new Date();
  var day = date.getDate();
  var month = date.getMonth() + 1;
  var year = date.getFullYear();
  if (month < 10) month = "0" + month;
  if (day < 10) day = "0" + day;
  return year + "-" + month + "-" + day;
}
var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['L','o','a','d','i','n','g'],
        datasets: []
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:false
                }
            }]
        }
    }
});
var colors = ['rgba(255,0,0,1)','rgba(0,255,0,1)','rgba(0,0,255,1)','rgba(255,255,0,1)','rgba(0,255,255,1)','rgba(255,255,255,1)']

buscarCotacaoGrafico();
function buscarCotacaoGrafico(){
  document.getElementsByName('graficoLoading')[0].style.display="block"
  document.getElementsByName('graficoContainer')[0].style.display="none"
  document.getElementsByName('graficoResultError')[0].style.display="none"
  var bank = '1,2,3';
  var date = today();
  var request = host + "/getQuoteList?provider=" + bank + "&date=" + date
  console.log(request);
  $.ajax({
    url: request,
    dataType: "json",
    success: function(data) {
      for (var i_bank = 0 ; i_bank < data.length ; i_bank++) {
        console.log(data);
        var cotacao = [];
        var ptax = [];
        var labels = []
        for (var i = data[i_bank].data.length - 1; i >= 0 ; i--) {
          cotacao.push(data[i_bank].data[i].quote);
          ptax.push(data[i_bank].data[i].ptax);
          labels.push(data[i_bank].data[i].date);

        }
        myChart.data.datasets.push({
            label: data[i_bank].providerName,
            data: cotacao,
            borderColor: [
                colors[i_bank]
            ],
            borderWidth: 1
        });
      }
      myChart.data.datasets.push({
          label: 'Ptax',
          data: ptax,

          borderColor: [
              'rgba(0,0,0,1)'
          ],
          borderWidth: 1
      });
      myChart.data.labels = labels;
      myChart.update();
      document.getElementsByName('graficoContainer')[0].style.display="block"
      document.getElementsByName('graficoLoading')[0].style.display="none"
      document.getElementsByName('graficoResultError')[0].style.display="none"
    },
    error: function(thrownError) {
      document.getElementsByName('graficoResultError')[0].style.display="block"
      document.getElementsByName('graficoLoading')[0].style.display="none"
      document.getElementsByName('graficoContainer')[0].style.display="none"
    }
  });

}

var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['L','o','a','d','i','n','g'],
        datasets: []
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:false
                }
            }]
        }
    }
});
