import { useEffect, useState } from "react";
import apiClient from "../api/axios";
import { Table, Button, Form } from "react-bootstrap";
function ManageEmployees() {
  const [availableEmployees, setAvailableEmployees] = useState([]);
  const [curentEmployees, setCurentEmployees] = useState([]);
  const [dropdownEmployeeId, setDropdownEmployeeId] = useState("");
  const [refresh, setRefresh] = useState(false);

  const queryParams = new URLSearchParams(location.search);
  const salonId = queryParams.get("salon_id");

  const handleDelete = async (id) => {
    await apiClient.delete(`/salons/${salonId}/remove/${id}`);
    setRefresh((refresh) => !refresh);
  };

  const handleAdd = async () => {
    await apiClient.post(`/salons/${salonId}/assign/${dropdownEmployeeId}`);
    setRefresh((refresh) => !refresh);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const freeEmployees = await apiClient.get("users/available-employees");
        const actualEmployees = await apiClient.get(
          `salons/${salonId}/employees`
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
      <h1>Angajatii vacanti</h1>
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
      <Button onClick={handleAdd}>Adauga</Button>

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
                  <Button onClick={() => handleDelete(employee.id)}>
                    Delete
                  </Button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </Table>
    </>
  );
}

export default ManageEmployees;
