import { useEffect, useState } from 'react';
import apiClient from '../api/axios';
import { useNavigate } from 'react-router-dom';

function MySalon() {
    const [errorMessage, setErrorMessage] = useState("");

     const navigate = useNavigate();

     useEffect(() => {
        const fetchData = async () => {
          try {
            const salonResponse = await apiClient.get(`/salons/search/by-logged-in-manager`, {
              headers: {
                Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
              }
            });
            
            navigate(`/salons/${salonResponse.data.id}`)
          } catch (error) {
            if(error.status === 404) {
                navigate(`/add_new_salon`)
            }
            
            setErrorMessage("Eroare");
          }
        };
        fetchData();
      }, []);
    
    return (
        <div>
            {errorMessage} 
        </div>
    )
}

export default MySalon
