import React, { useState, useEffect } from 'react'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';


import { Pie } from 'react-chartjs-2';


ChartJS.register(ArcElement, Tooltip, Legend);



const PieChart = () => {
  const [chart, setChart] = useState({});
  var baseUrl = "http://localhost:9000/api/transactions?page=0&size=20";
  // var proxyUrl = "https://cors-anywhere.herokuapp.com/";
  // var apiKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";



  useEffect(() => {
    const fetchCoins = async () => {
      await fetch(`${baseUrl}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
         
        'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY0MjMzMDUxNn0.Nm2MYlzXLtdI6HsmzgkTNqZopfR8jlDJ0Ft1169a5ZqY0C39LjHNO6vaS-auDYwcucSaMAq4JGuZjFMstiQFNQ'
          
         
        }
      })
        .then((response) => {
          if (response.ok) {
            response.json().then((json) => {
              console.log(json.data);
              setChart(json.data)
            });
          }
        }).catch((error) => {
          console.log(error);
        });
    };
    fetchCoins()
  }, [baseUrl])

  console.log("chart", chart);
  var data = {
    labels: ['Utilities', 'Food', 'Gas', 'Entertainment', 'Insurance', 'Internet','ZipCode'], // chart?.category?.categoryName?.map(x => x.categoryName),
    datasets: [{
      label: '# of Votes', // `${chart?.category?.length} Coins Available`,
      data: [12, 19, 3, 5, 2, 3,7], // chart?.amount?.map(x => x.amount),
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)',
        'rgba(200, 159, 64, 0.2)'
      ],
      borderColor: [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)',
        'rgba(200, 159, 64, 1)'
      ],
      borderWidth: 1
    }]
  };

  var options = {
    maintainAspectRatio: false,
    scales: {
    },
    legend: {
      labels: {
        fontSize: 25,
      },
    },
  }

  return (
    <div>
      <Pie
        data={data}
        height={400}
        options={options}

      />
    </div>
  )
}

export default PieChart
