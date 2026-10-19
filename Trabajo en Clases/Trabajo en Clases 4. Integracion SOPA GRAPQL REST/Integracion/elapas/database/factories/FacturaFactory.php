<?php

namespace Database\Factories;

use App\Models\Factura;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends Factory<Factura>
 */
class FacturaFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'numero_documento' => $this->faker->numerify('###########'),
            'nombre_completo' => $this->faker->name(),
            'numero_factura' => $this->faker->unique()->numberBetween(1000, 9999),
            'anio' => $this->faker->year(),
            'mes' => $this->faker->randomElement(['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre']),
            'total_factura' => $this->faker->randomFloat(2, 100, 10000),
            'estado' => $this->faker->randomElement(['emitida','pagada','anulada']),
            //
        ];
    }
}
