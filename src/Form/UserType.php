<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('userEmail')
            ->add('userName')
             ->add('userPassword', PasswordType::class)
              ->add('userPhone')
            ->add('userRole')
            ->add('userSexe')
            ->add('image', FileType::class, [
                'label' => 'Image (JPEG or PNG file)',
                'mapped' => false, // Set mapped to false since we handle file upload manually
                'required' => false, // Image upload is optional
            ]);
        
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
    
}
