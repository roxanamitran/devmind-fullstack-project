import { useEffect, useState } from "react";
import apiClient from "../api/axios";
import { Table, Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
function ManageEmployees() {
  const [availableEmployees, setAvailableEmployees] = useState([]);
  const [curentEmployees, setCurentEmployees] = useState([]);
  const [dropdownEmployeeId, setDropdownEmployeeId] = useState("");
  const [refresh, setRefresh] = useState(false);
  const navigate = useNavigate();

  const queryParams = new URLSearchParams(location.search);
  const salonId = queryParams.get("salon_id");

  const handleDelete = async (id) => {
    await apiClient.delete(`/salons/${salonId}/remove/${id}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
      }
    });
    setRefresh((refresh) => !refresh);
  };

  const handleAdd = async () => {
    await apiClient.post(`/salons/${salonId}/assign/${dropdownEmployeeId}`, 
      {},
      {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
      }
    });
    setRefresh((refresh) => !refresh);
  };

  function backToSalon() {
    navigate(`/salons/${salonId}`);
 
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        const freeEmployees = await apiClient.get("users/available-employees", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
          }
        });
        const actualEmployees = await apiClient.get(
          `salons/${salonId}/employees`, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
            }
          }
        );

        if (freeEmployees.data.length > 0) {
          setDropdownEmployeeId(freeEmployees.data[0].id);
        }

        setAvailableEmployees(freeEmployees.data);
        setCurentEmployees(actualEmployees.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, [refresh]);

  return (
    <>
    <div className="centrat"><h1>Angajatii vacanti</h1></div>
      <div className="form4">
      <Form.Select
        type="text"
        value={dropdownEmployeeId}
        onChange={(e) => setDropdownEmployeeId(e.target.value)}
      >
        {availableEmployees.map((employee) => (
          <option key={employee.id} value={employee.id}>
            {employee.email}
          </option>
        ))}
      </Form.Select>
      <Button  variant = "success" onClick={handleAdd}>Adauga angajat</Button>

      <h3>Persoanele angajate la acest salon</h3>
      <Table>
        <thead>
          <tr>
            <th>Prenumele</th>
            <th>Numele</th>
            <th>Adresa de email</th>
          </tr>
        </thead>
        <tbody>
          {curentEmployees.map((employee) => {
            return (
              <tr key={employee.id}>
                <td>{employee.firstName}</td>
                <td>{employee.lastName}</td>
                <td>{employee.email}</td>
                <td>
                  <Button  variant = "danger" onClick={() => handleDelete(employee.id)}>
                    Sterge angajat
                  </Button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </Table>
      <Button variant="dark" onClick={backToSalon}>Inapoi la salon</Button>
      </div>
    </>
  );
}

export default ManageEmployees;
