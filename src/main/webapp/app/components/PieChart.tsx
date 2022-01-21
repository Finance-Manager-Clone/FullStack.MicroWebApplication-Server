import React, { useState, useEffect } from 'react'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import axios from 'axios';

import { Pie } from 'react-chartjs-2';


ChartJS.register(ArcElement, Tooltip, Legend);

 const baseURL = "http://localhost:9000/api/transactions?page=0&size=20";
// const baseURL = "https://zip-code-bank.herokuapp.com/api/transactions?page=0&size=20";

const JWT = "Bearer "+ sessionStorage.getItem('jhi-authenticationToken');

const PieChart = () => { 
const [transaction, setTransaction] = useState([]);
  
  useEffect(() => {
    axios.get(baseURL, { 'headers': { 'Authorization': JWT } }).then((response) => {
      
      setTransaction(response.data);
    //  console.log(response.data);
    });
  }, []);

  if (!transaction) return null;

 
    const category = transaction.map(x => x.category.categoryName) // array of categories
    const amount =  transaction.map(x => x.amount) // array of amounts

    var obj = {};
      
    // Using loop to insert key
    // value in Object
    for(var i = 0; i < category.length; i++){
         const value = amount[i]
        if(obj[category[i]]){
          obj[category[i]] += value
        }
       else{obj[category[i]] = value;}
  }
  // creates an array of objects
  var categoriesArrayObj = Object.entries(obj).map(([key, value]) => ({key,value}));
  
    console.log("-------------")
   // console.log(category)
   // console.log(amount)
   // console.log(obj)
   console.log(categoriesArrayObj)
    console.log("-------------")

  var data = {
    labels:  categoriesArrayObj.map(x => x.key), // transaction.map(cat => cat.category.categoryName),
    datasets: [{
      label: '# of Categories', 
      data: categoriesArrayObj.map(x => x.value) , // transaction.map(amt => amt.amount), 
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)',
        'rgba(200, 159, 64, 0.2)',
        'rgba(190, 150, 64, 0.2)',
        'rgba(160, 140, 54, 0.2)',
        'rgba(140, 140, 84, 0.2)'
      ],
      borderColor: [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)',
        'rgba(200, 159, 64, 1)',
        'rgba(190, 150, 64, 1)',
        'rgba(160, 140, 54, 1)',
        'rgba(140, 140, 84, 1)'
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
