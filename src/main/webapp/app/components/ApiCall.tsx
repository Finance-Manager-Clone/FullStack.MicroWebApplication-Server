import axios from "axios";
import React,{useState} from "react";

const baseURL = "http://localhost:9000/api/transactions?page=0&size=20";
const JWT = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY0MjMzMDUxNn0.Nm2MYlzXLtdI6HsmzgkTNqZopfR8jlDJ0Ft1169a5ZqY0C39LjHNO6vaS-auDYwcucSaMAq4JGuZjFMstiQFNQ";

export default function ApiCall() {

  const [transaction, setTransaction] = useState(null);

  React.useEffect(() => {
    axios.get(baseURL, { 'headers': { 'Authorization': JWT } }).then((response) => {
      
      setTransaction(response.data);
      console.log(response.data);
    });
  }, []);

  if (!transaction) return null;

  return (
    <div>
    {transaction.map(x => console.log( "Amount: $"+x.amount) )}
    {transaction.map(x => console.log( "Category: "+x.category.categoryName) )}
      <h1>{transaction[1].amount}</h1>
      <h2>{transaction[1].category.categoryName}</h2>

    </div>
  );
}