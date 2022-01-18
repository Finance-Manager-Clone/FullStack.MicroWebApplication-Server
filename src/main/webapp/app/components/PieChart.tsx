import React, { useState, useEffect } from 'react'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import axios from 'axios';

import { Pie } from 'react-chartjs-2';


ChartJS.register(ArcElement, Tooltip, Legend);

const baseURL = "http://localhost:9000/api/transactions?page=0&size=20";

const JWT = "Bearer "+ sessionStorage.getItem('jhi-authenticationToken');

const PieChart = () => { 
const [transaction, setTransaction] = useState([]);
  
  useEffect(() => {
    axios.get(baseURL, { 'headers': { 'Authorization': JWT } }).then((response) => {
      
      setTransaction(response.data);
      console.log(response.data);
    });
  }, []);

  if (!transaction) return null;


  var data = {
    labels:  transaction.map(category => category.category.categoryName),
    datasets: [{
      label: '# of Categories', 
      data: transaction.map(amt => amt.amount), 
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
