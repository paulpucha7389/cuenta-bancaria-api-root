/**
 * 
 */
package org.paulpucha.api.cuenta.bancaria.cuenta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.paulpucha.api.cuenta.bancaria.cuenta.model.entity.Movimiento;
import org.springframework.data.repository.query.Param;

/**
 * 
 * <b> Interfaz del repositorio del movimiento. </b>
 * 
 * @author Paul Pucha
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: Paul Pucha $, $Date: 08 jul. 2024 $]
 *          </p>
 */
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

	List<Movimiento> findByIdCuenta(Long idCuenta);

	@Modifying
	@Query(value = "DELETE FROM Movimiento m WHERE m.id_movimiento = :idMovimiento", nativeQuery = true)
	void deleteByIdMovimiento(@Param("idMovimiento") Long idMovimiento);

}
