import axios from "axios";
import React,{useState, useEffect} from "react";

const baseURL = "http://localhost:9000/api/transactions?page=0&size=20";

const JWT = "Bearer "+ sessionStorage.getItem('jhi-authenticationToken');
console.log(JWT);
export default function ApiCall() {

  const [transaction, setTransaction] = useState(null);

  useEffect(() => {
    axios.get(baseURL, { 'headers': { 'Authorization': JWT } }).then((response) => {
      
      setTransaction(response.data);
      console.log(response.data);
    });
  }, []);

  if (!transaction) return null;

  return (
    <div>
      {console.log("Hello "+JWT)}
    {transaction.map(x => console.log( "Amount: $"+x?.amount) )}
    {transaction.map(x => console.log( "Category: "+x?.category?.categoryName) )}
      <h1>{transaction[1]?.amount}</h1>
      <h2>{transaction[1]?.category?.categoryName}</h2>

    </div>
  );
}