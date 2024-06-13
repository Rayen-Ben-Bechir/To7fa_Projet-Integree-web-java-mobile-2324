<?php

namespace App\Entity;

use App\Repository\CategoryRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CategoryRepository::class)]
class Category
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id_cat;

    #[ORM\Column(type: 'string', length: 255)]
    private $nom_cat;

    // Define getters and setters for properties

    public function getIdCat(): ?int
    {
        return $this->id_cat;
    }

    public function getNomCat(): string
    {
        return $this->nom_cat;
    }

    public function setNomCat(string $nom_cat): self
    {
        $this->nom_cat = $nom_cat;
        return $this;
    }

    // Other methods as needed
}
