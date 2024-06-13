<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\UserType;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\HttpKernel\KernelInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Core\User\PasswordAuthenticatedUserInterface;
use Dompdf\Dompdf;
use Dompdf\Options;


class UserController extends AbstractController
{
    #[Route('/User/add', name: 'add_user')]
    public function addUser(Request $request, EntityManagerInterface $entityManager,KernelInterface $kernel): Response
    {
        // Récupérer les données depuis la requête
        $user = new User();
     
        $form = $this->createFormBuilder($user)
            ->add('userEmail')
            ->add('userName')
            ->add('userPassword', RepeatedType::class, [
                'type' => PasswordType::class,
                'invalid_message' => 'Les deux mots de passe doivent correspondre.',
                'first_options' => ['label' => 'Mot de passe'],
                'second_options' => ['label' => 'Confirmez le mot de passe'],
            ])
            ->add('userPhone', null, [
                'attr' => [
                    'pattern' => '[0-9]{10}',
                    'title' => 'Veuillez saisir un numéro de téléphone valide.',
                ],
            ])
            ->add('userSexe', ChoiceType::class, [
                'choices' => [
                    'Femme' => 'Femme',
                    'Homme' => 'Homme',
                ],
                'label' => 'Sexe',
                'placeholder' => 'Sélectionnez votre sexe', // Optional placeholder
            ])
            ->add('image', FileType::class, [
                'label' => 'Image (JPEG or PNG file)',
                'mapped' => false, // Set mapped to false since we handle file upload manually
                'required' => false, // Image upload is optional
            ])
            ->getForm();
    
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
    
            $existingUser = $entityManager->getRepository(User::class)->findOneBy(['userEmail' => $user->getUserEmail()]);
    
            if ($existingUser) {
                // Add a flash message to inform the user
                $this->addFlash('error', 'Email already exists. Please choose a different email.');
    
                // Redirect back to the registration page with the error message
                return $this->redirectToRoute('add_user');
            }
            $uploadedFile = $form['image']->getData();
        
            if ($uploadedFile) {
                $imageDirectory = $kernel->getProjectDir() . '/public/images'; // Your image directory
                $newFilename = uniqid() . '.' . $uploadedFile->guessExtension();
    
                try {
                    $uploadedFile->move($imageDirectory, $newFilename);
                } catch (FileException $e) {
                    // Handle the file upload exception
                }
    
                $user->setUserImage($newFilename);
            } else {
                // Set a default image if no image is uploaded
                $user->setUserImage('default_image.jpg');
            }
            // Hash the password using password_hash
            $plainPassword = $user->getUserPassword();
            $hashedPassword = password_hash($plainPassword, PASSWORD_DEFAULT);
            $user->setUserPassword($hashedPassword);
    
            // Assign ROLE_USER directly
            $user->setUserRole('ROLE_USER');
    
            // Persist the user entity
            $entityManager->persist($user);
            $entityManager->flush();
    
            return $this->redirectToRoute('app_connect');
        }
    
        // Retourner une réponse de succès ou rediriger vers une autre page
        return $this->render('user/addUser.html.twig', [
            'form' => $form->createView(),
        ]);
    }





/*
#[Route('/User/edit/{id}', name: 'edit_user')]
public function editUser(Request $request, EntityManagerInterface $entityManager, User $user): Response
{
    // Récupérer les données depuis la requête

    $form = $this->createFormBuilder($user)
        ->add('userEmail')
        ->add('userName')
        ->add('userPassword', PasswordType::class)
        ->add('userPhone')
        
        ->add('userSexe', ChoiceType::class, [
            'choices' => [
                'Femme' => 'Femme',
                'Homme' => 'Homme',
            ],
            'label' => 'Sexe',
            'placeholder' => 'Sélectionnez votre sexe', // Optional placeholder
        ])    
           
        ->getForm();

    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        // Hash the password using password_hash
        $plainPassword = $user->getUserPassword();
        $hashedPassword = password_hash($plainPassword, PASSWORD_DEFAULT);
        $user->setUserPassword($hashedPassword);
        $user->setUserRole('ROLE_USER');

        // Persist the user entity
        $entityManager->flush();

        return $this->redirectToRoute('app_connect');
    }

    return $this->render('user/editUser.html.twig', [
        'form' => $form->createView(),
        'user' => $user,
    ]);
}
*/

#[Route('/User/edit/{id}', name: 'edit_user')]
public function editUser(Request $request, EntityManagerInterface $entityManager, User $user, KernelInterface $kernel): Response
{
    // Create the form with the user data
    $form = $this->createFormBuilder($user)
        ->add('userEmail')
        ->add('userName')
        ->add('userPassword', PasswordType::class)
        ->add('userPhone')
        ->add('userSexe', ChoiceType::class, [
            'choices' => [
                'Femme' => 'Femme',
                'Homme' => 'Homme',
            ],
            'label' => 'Sexe',
            'placeholder' => 'Sélectionnez votre sexe',
        ])
        ->add('image', FileType::class, [
            'label' => 'Image (JPEG or PNG file)',
            'mapped' => false,
            'required' => false,
        ])
        ->getForm();

    // Handle form submission
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        // Handle image upload
        $uploadedFile = $form['image']->getData();

        if ($uploadedFile) {
            $imageDirectory = $kernel->getProjectDir() . '/public/images'; // Your image directory
            $newFilename = uniqid() . '.' . $uploadedFile->guessExtension();

            try {
                $uploadedFile->move($imageDirectory, $newFilename);
            } catch (FileException $e) {
                // Handle the file upload exception
            }

            $user->setUserImage($newFilename);
        }

        // Hash the password if it has changed
        $plainPassword = $user->getUserPassword();
        if (!empty($plainPassword)) {
            $hashedPassword = password_hash($plainPassword, PASSWORD_DEFAULT);
            $user->setUserPassword($hashedPassword);
        }

        // Persist the changes
        $entityManager->flush();

        // Redirect after successful update
        return $this->render('user/editUser.html.twig', [
            'form' => $form->createView(),
            'user' => $user,
        ]);
    }


    // Render the edit user form
    return $this->render('user/editUser.html.twig', [
        'form' => $form->createView(),
        'user' => $user,
    ]);
}








    #[Route('/admin/search', name: 'admin_search')]
    public function adminSearch(Request $request, UserRepository $userRepository): Response
    {
        $searchTerm = $request->query->get('search');

        // Utilisez le repository pour rechercher les utilisateurs en fonction du terme de recherche
        $users = $userRepository->findBySearchTerm($searchTerm);

        return $this->render('admin/homeAdmin.html.twig', [
            'users' => $users,
        ]);
    }
    





    #[Route('/admin/pdf', name: 'admin_pdf')]
    public function adminPdf(UserRepository $userRepository): Response
    {
        $users = $userRepository->findAll();
    
        // Create a new DOMpdf instance
        $options = new Options();
        $options->set('defaultFont', 'Arial');
        $dompdf = new Dompdf($options);
    
        // Render the HTML table as a string
        $html = $this->renderView('admin/pdfTable.html.twig', [
            'users' => $users,
        ]);
    
        // Load the HTML string into the DOMpdf instance
        $dompdf->loadHtml($html);
    
        // Render the PDF
        $dompdf->render();
    
        // Get the PDF content
        $pdfContent = $dompdf->output();
    
        // Set the response headers for download
        $response = new Response($pdfContent);
        $response->headers->set('Content-Type', 'application/pdf');
        $response->headers->set('Content-Disposition', 'attachment; filename="user_list.pdf"');
    
        return $response;
    }
    
    #[Route('/users/table', name: 'users_table')]
    public function usersTable(UserRepository $userRepository): Response
    {
        $users = $userRepository->findAll(); // Fetch users from the repository
    
        return $this->render('admin/usersTable.html.twig', [
            'users' => $users, // Pass the users variable to the template
        ]);
    }
    #[Route('/User/delete/{id}', name: 'delete_user')]
public function deleteUser(User $user, EntityManagerInterface $entityManager): Response
{
    $entityManager->remove($user);
    $entityManager->flush();

    // Redirect to a specific route after deletion (e.g., homepage)
    return $this->redirectToRoute('app_connect');
}
}    